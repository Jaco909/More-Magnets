package bigmagnet.items.potions;

import necesse.entity.mobs.PlayerMob;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class InfiniteGreaterMagnetPotionItem extends SimplePotionItem {
    public InfiniteGreaterMagnetPotionItem() {
        super(1, Rarity.LEGENDARY, "greatermagnetpotion", 30000, new String[]{"greatermagnetpot"});
        this.setItemCategory(ItemCategory.craftingManager, new String[]{"consumable", "utilitypotions"});
        this.overridePotion("magnetpotion");
    }
    public boolean isSingleUse(PlayerMob player) {
        return false;
    }
}