package bigmagnet.magnetchest;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.interfaces.OEUsers;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.SharedTextureDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.ObjectDamagedTextureArray;
import necesse.level.gameObject.container.CraftingStationObject;
import necesse.level.gameObject.container.InventoryObject;
import necesse.level.gameObject.container.StorageBoxInventoryObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class MagnetChest extends StorageBoxInventoryObject {
    public GameTexture texture;
    public GameTexture texture_off;

    public MagnetChest() {
        super("magnetchest",40,ToolType.ALL,new Color(227, 46, 40));
        this.rarity = Item.Rarity.EPIC;
    }
    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.textureName);
        this.texture_off = GameTexture.fromFile("objects/" + this.textureName + "_off");
    }


    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = level.wireManager.isWireActiveAny(tileX, tileY) ? this.texture_off : this.texture;
        if (this.openTexture != null) {
            ObjectEntity ent = level.entityManager.getObjectEntity(tileX, tileY);
            if (ent != null && ent.implementsOEUsers() && ((OEUsers)ent).isInUse()) {
                texture = this.openTexture;
            }
        }

        final SharedTextureDrawOptions draws = (new SharedTextureDrawOptions(texture));
        int rotation = level.getObjectRotation(tileX, tileY) % (texture.getWidth() / 32);
        boolean treasureHunter = perspective != null && (Boolean)perspective.buffManager.getModifier(BuffModifiers.TREASURE_HUNTER);
        draws.addSprite(rotation, 0, 32, texture.getHeight()).spelunkerLight(light, treasureHunter, (long) this.getID(), level).pos(drawX, drawY - texture.getHeight() + 32);

        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                draws.draw();
            }
        });
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "magnetChestTip"), 400);
        tooltips.add(Localization.translate("itemtooltip", "magnetChestWireTip"), 400);
        return tooltips;
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "usetip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }


    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new MagnetChestObjectEntity(level, x, y);
    }
}
