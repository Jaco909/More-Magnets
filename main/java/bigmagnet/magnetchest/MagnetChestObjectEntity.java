package bigmagnet.magnetchest;

import necesse.engine.GameLog;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.save.levelData.InventorySave;
import necesse.engine.util.GameRandom;
import necesse.entity.objectEntity.InventoryObjectEntity;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.interfaces.OEInventory;
import necesse.entity.particle.Particle;
import necesse.entity.pickup.ItemPickupEntity;
import necesse.entity.pickup.PickupEntity;
import necesse.inventory.Inventory;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.ArrayList;

public class MagnetChestObjectEntity extends InventoryObjectEntity {

    public MagnetChestObjectEntity(Level level, int x, int y) {
        super(level,x,y,40);
    }

    public void clientTick() {
        super.clientTick();
        if (!this.getLevel().wireManager.isWireActiveAny(this.tileX,this.tileY)) {
            this.getLevel().entityManager.pickups.getInRegionByTileRange(this.tileX, this.tileY, 7).forEach(this::moveEntity);
        }
    }

    public void serverTick() {
        super.serverTick();
        if (!this.getLevel().wireManager.isWireActiveAny(this.tileX,this.tileY)) {
            this.getLevel().entityManager.pickups.getInRegionByTileRange(this.tileX, this.tileY, 7).forEach(this::moveEntity);
        }
    }

    public void moveEntity(PickupEntity entity){
        if (entity instanceof ItemPickupEntity) {
            ItemPickupEntity itemPickup = (ItemPickupEntity) entity;

            if (this.inventory.canAddItem(this.getLevel(),null,itemPickup.item,"test") == itemPickup.item.getAmount()) {
                this.inventory.addItem(this.getLevel(),null,itemPickup.item,"item");
                this.getLevel().entityManager.pickups.get(entity.getUniqueID(),true).remove();
                if (this.isClient()) {
                    double dx = this.tileX - entity.getTileX();
                    double dy = this.tileY - entity.getTileY();
                    float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
                    for (int i = 0; i < 9; i++) {
                        this.getLevel().entityManager.addParticle(entity.x + (float) (GameRandom.globalRandom.nextGaussian() * (double) 6.0F), entity.y + (float) (GameRandom.globalRandom.nextGaussian() * (double) 8.0F), Particle.GType.IMPORTANT_COSMETIC).color(new Color(255, 255, 255)).height(16.0F).lifeTime(850 + GameRandom.globalRandom.getIntBetween(0, 100)).givesLight(85).size((options, lifeTime, timeAlive, lifePercent) -> options.size(12)).sizeFades(1, 10).fadesAlphaTime(1, 500);
                    }
                    for (int i = 0; i < 9; i++) {
                        this.getLevel().entityManager.addParticle(entity.x + (float) (GameRandom.globalRandom.nextGaussian() * (double) 6.0F), entity.y + (float) (GameRandom.globalRandom.nextGaussian() * (double) 8.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstantAngle(angle, 60 + GameRandom.globalRandom.getIntBetween(0, 10)).color(new Color(205, 90, 90)).height(16.0F).lifeTime(850 + GameRandom.globalRandom.getIntBetween(0, 100)).givesLight(85).size((options, lifeTime, timeAlive, lifePercent) -> options.size(12)).sizeFades(1, 10).fadesAlphaTime(1, 500);
                    }
                }
            }
        }
    }

}