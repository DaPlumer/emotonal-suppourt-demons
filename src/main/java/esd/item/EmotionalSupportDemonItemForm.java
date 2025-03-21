package esd.item;

import esd.entity.ModEntities;
import esd.entity.custom.DemonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Spawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;


public class EmotionalSupportDemonItemForm extends SpawnEggItem {
    public EmotionalSupportDemonItemForm(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Settings settings) {
        super(type, primaryColor, secondaryColor, settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof Spawner spawner) {
                spawner.setEntityType(ModEntities.DEMON, world.getRandom());
                world.updateListeners(blockPos, blockState, blockState, 3);
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.decrement(1);
                return ActionResult.CONSUME;
            } else {
                BlockPos blockPos2;
                if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                    blockPos2 = blockPos;
                } else {
                    blockPos2 = blockPos.offset(direction);
                }
                DemonEntity childEntity = ModEntities.DEMON.spawn((ServerWorld) world,blockPos2,SpawnReason.SPAWN_EGG);
                if (childEntity != null) {
                    setupChildEntity(childEntity, context.getPlayer(), itemStack, (ServerWorld) world);
                }

                return ActionResult.CONSUME;
            }
        }
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(itemStack);
            } else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
                DemonEntity childEntity = ModEntities.DEMON.spawn((ServerWorld) world,blockPos,SpawnReason.SPAWN_EGG);
                if (childEntity == null) {
                    return TypedActionResult.pass(itemStack);
                } else {
                    setupChildEntity(childEntity, user, itemStack, (ServerWorld) world);
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.consume(itemStack);
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }
    private static void setupChildEntity(DemonEntity childEntity,PlayerEntity spawner,ItemStack stack, ServerWorld world) {
        childEntity.applyOrientation(stack.getOrDefault(DataTypes.ORENTATION, "_"));
        stack.decrementUnlessCreative(1, spawner);
        world.emitGameEvent(spawner, GameEvent.ENTITY_PLACE, childEntity.getPos());
    }
}