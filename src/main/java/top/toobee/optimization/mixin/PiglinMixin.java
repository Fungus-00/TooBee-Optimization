package top.toobee.optimization.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.toobee.optimization.cache.CachedMob;
import top.toobee.optimization.cache.PiglinCache;

@Mixin(PiglinEntity.class)
public abstract class PiglinMixin extends Entity implements CachedMob<PiglinEntity, PiglinCache> {
    protected PiglinMixin(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private PiglinCache cache = null;

    @Override
    public void tick() {
        final var self = (PiglinEntity) (Object) this;
        if (this.cache == null || !this.cache.checkCondition(self))
            this.toobee$updateCache(PiglinCache.Companion.findCache((ServerWorld) this.getWorld(), self.getBlockPos()));
        super.tick();
        if (this.cache != null)
            this.cache.setHasUpdatedThisTick(true);
    }

    @Override
    public PiglinCache toobee$getCache() {
        return this.cache;
    }

    @Override
    public void toobee$setCache(PiglinCache cache) {
        this.cache = cache;
    }
}
