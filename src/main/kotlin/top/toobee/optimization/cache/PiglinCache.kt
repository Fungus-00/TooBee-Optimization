package top.toobee.optimization.cache

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.Brain
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.mob.AbstractPiglinEntity
import net.minecraft.entity.mob.HoglinEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.PiglinEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import java.util.Optional

@Suppress("PrivatePropertyName")
class PiglinCache private constructor(
    world: ServerWorld,
    pos: BlockPos,
): StackingMobCache<PiglinEntity>(world, pos) {
    companion object : Caches<PiglinEntity, PiglinCache>(PiglinEntity::class.java) {
        override fun create(world: ServerWorld, pos: BlockPos): PiglinCache = PiglinCache(world, pos)
    }

    override fun truncate() {
        super.truncate()
        all.remove(this.world to this.pos)
    }

    private var NEAREST_REPELLENT = Optional.empty<BlockPos>()
    private var NEAREST_VISIBLE_NEMESIS = Optional.empty<MobEntity>()
    private var NEAREST_VISIBLE_HUNTABLE_HOGLIN = Optional.empty<HoglinEntity>()
    private var NEAREST_VISIBLE_BABY_HOGLIN = Optional.empty<HoglinEntity>()
    private var NEAREST_VISIBLE_ZOMBIFIED = Optional.empty<LivingEntity>()
    private var NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD = Optional.empty<PlayerEntity>()
    private var NEAREST_PLAYER_HOLDING_WANTED_ITEM = Optional.empty<PlayerEntity>()
    private var NEARBY_ADULT_PIGLINS = Optional.empty<List<AbstractPiglinEntity>>()
    private var NEAREST_VISIBLE_ADULT_PIGLINS = Optional.empty<List<AbstractPiglinEntity>>()
    private var VISIBLE_ADULT_PIGLIN_COUNT = Optional.empty<Int>()
    private var VISIBLE_ADULT_HOGLIN_COUNT = Optional.empty<Int>()

    override fun newSense(world: ServerWorld, entity: MobEntity) {
        val brain = entity.brain
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, this.NEAREST_REPELLENT)
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, this.NEAREST_VISIBLE_NEMESIS)
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, this.NEAREST_VISIBLE_HUNTABLE_HOGLIN)
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, this.NEAREST_VISIBLE_BABY_HOGLIN)
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, this.NEAREST_VISIBLE_ZOMBIFIED)
        brain.remember(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, this.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)
        brain.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, this.NEAREST_PLAYER_HOLDING_WANTED_ITEM)
        brain.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, this.NEARBY_ADULT_PIGLINS)
        brain.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, this.NEAREST_VISIBLE_ADULT_PIGLINS)
        brain.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, this.VISIBLE_ADULT_PIGLIN_COUNT)
        brain.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, this.VISIBLE_ADULT_HOGLIN_COUNT)
    }

    fun reset(brian: Brain<PiglinEntity>) {
        this.NEAREST_REPELLENT = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_REPELLENT)
        this.NEAREST_VISIBLE_NEMESIS = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS)
        this.NEAREST_VISIBLE_HUNTABLE_HOGLIN = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN)
        this.NEAREST_VISIBLE_BABY_HOGLIN = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN)
        this.NEAREST_VISIBLE_ZOMBIFIED = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)
        this.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)
        this.NEAREST_PLAYER_HOLDING_WANTED_ITEM = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM)
        this.NEARBY_ADULT_PIGLINS = brian.getOptionalRegisteredMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS)
        this.NEAREST_VISIBLE_ADULT_PIGLINS = brian.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS)
        this.VISIBLE_ADULT_PIGLIN_COUNT = brian.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT)
        this.VISIBLE_ADULT_HOGLIN_COUNT = brian.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT)
    }
}