package esd.entity.custom;
import esd.CustomSounds;
import esd.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class DemonEntity extends AnimalEntity{

    private static final TrackedData<String> ORIENTATION =
            DataTracker.registerData(DemonEntity.class, TrackedDataHandlerRegistry.STRING);
    public String askOrientation(){
        if(!types.contains(this.dataTracker.get(ORIENTATION))) this.randomizeOrientation();
        return this.dataTracker.get(ORIENTATION);
    }
    public void applyOrientation(String orientation){
            this.dataTracker.set(ORIENTATION, orientation);
    }

    private int sitTicks = 0;
    public int getSitTicks(){
        return this.sitTicks;
    }

    @Override
    public int getSafeFallDistance() {
        return (int) (super.getSafeFallDistance() * 2.75);
    }

    public final AnimationState sit = new AnimationState();
    public final AnimationState idle = new AnimationState();
    private int idleAnimationTimeout = 0;

    public DemonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
    }
    List<String> types = List.of(
            "_",
            "gay_",
            "gayer_",
            "ace_",
            "pan_",
            "trans_",
            "gender_queer_",
            "aero_",
            "bi_",
            "lesbian_",
            "non_binary_"

    );
    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.CAKE), false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(4, new FollowMobGoal(this, 0.25D, 0.0F, 50.0F));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }
    public static DefaultAttributeContainer.Builder createAttributes(){
        return  MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)

                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 7.0D);
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
                this.idle.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }
    @Override
    public void tick() {
        super.tick();
        if(attackingPlayer != null){
            attackingPlayer.kill();
        }

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
            updateSitTick();
        }
        if(this.hasVehicle()){
            this.getVehicle().updatePassengerPosition(this);
        }
        if(this.getVehicle() instanceof PlayerEntity player && player.isSneaking()){
            this.dismountVehicle();
        }
        if(isFallFlying() && startFall){
                startFall = false;
                this.playSound(CustomSounds.START_FALL);

        }
        if(!isFallFlying()) startFall=true;

    }


    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        if(source.isOf(DamageTypes.FALL)) return CustomSounds.CRASH;
        return super.getHurtSound(source);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CAKE) && !Objects.equals(this.askOrientation(), "ace_")&& !Objects.equals(this.askOrientation(), "aero_");
    }
    private void updateSitTick(){
        /*
        A function run each tick to update the entities' sitting position.
        When the sitTicks variable is equal to zero, the emotional support demon is either standing or walking.
        Each tick there is a random chance to start the sitting animation by setting the sitTicks variable to 1.
        the variable counts down each tick from 20 to 0 while playing the sit down animation.
        When the sitTicks value hits 0, it becomes a random number between -128 and -256.
        when sitTicks hits -20, it Starts the standing animation.
         */

        if (random.nextInt(100) == 0 && sitTicks == 0 &! this.hasVehicle()) {
            this.sit.start(0);
            this.sitTicks = 20;
        }
        if (sitTicks > 0) {
            sitTicks--;
            if (sitTicks == 0) sitTicks = -(random.nextInt(128) + 128); //
        }
        if (sitTicks < 0){
            sitTicks++;
            if (sitTicks == -20) this.sit.start(0);
        }
    }
    private static boolean startFall = true;
    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DemonEntity baby = ModEntities.DEMON.create(world);
        if(baby == null) return null;
        if(random.nextBoolean()) {
            baby.applyOrientation(this.askOrientation());
        }else {
            baby.applyOrientation(types.get(random.nextInt(types.size())));
        }
        return baby;
    }
    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!player.isSneaking() && !this.hasVehicle() && this.sitTicks == 0) this.startRiding(player);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Variant", this.askOrientation());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(ORIENTATION, nbt.getString("Variant"));
    }
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        randomizeOrientation();
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ORIENTATION, "_");
    }
    public void randomizeOrientation(){
        applyOrientation(types.get(random.nextInt(types.size())));}
}
