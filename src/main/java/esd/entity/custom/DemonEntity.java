package esd.entity.custom;

import esd.entity.ModEntities;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class DemonEntity extends AnimalEntity {
    private final List<String> types = List.of(
            "",
            "gay_",
            "gayer_",
            "gayest_",
            "ace_",
            "pan_",
            "trans_",
            "gender_queer_",
            "aero_",
            "bi_",
            "lesbian_",
            "non_binary_"

    );
    private String orientation;
    public String getOrientation(){
        return this.orientation;
    }
    private int sitTicks = 0;
    public int getSitTicks(){
        return this.sitTicks;
    }
    public final AnimationState sit = new AnimationState();
    public final AnimationState idle = new AnimationState();
    private int idleAnimationTimeout = 0;
    public DemonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.orientation = this.types.get((int) (Math.random() * this.types.size()));
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.CAKE), false));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(4, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }
    public static DefaultAttributeContainer.Builder createAttributes(){
        return  MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D);
    }

    @Override
    public boolean isDead() {
        return false;
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

            if (random.nextInt(100) == 0 && sitTicks == 0) {
                this.sit.start(0);
                this.sitTicks = 21;
            }
            if (sitTicks > 0) {
                --sitTicks;
            }
            if (sitTicks == 1){
                sitTicks = -random.nextInt(100) - 50;
            }
            if (sitTicks < 0){
                this.sit.start(0);
                sitTicks++;
            }
        }

    }
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CAKE) && !Objects.equals(this.orientation, "ace_")&& !Objects.equals(this.orientation, "aero_");
    }



    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.DEMON.create(world);
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        if (this.sitTicks == 0) {
            super.move(movementType, movement);
        }
    }
}
