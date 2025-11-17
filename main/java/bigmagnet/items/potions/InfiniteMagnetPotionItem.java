package bigmagnet.items.potions;

import necesse.entity.mobs.PlayerMob;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class InfiniteMagnetPotionItem extends SimplePotionItem {
    public InfiniteMagnetPotionItem() {
        super(1, Rarity.LEGENDARY, "magnetpotion", 30000, new String[]{"magnetpot"});
        this.setItemCategory(ItemCategory.craftingManager, new String[]{"consumable", "utilitypotions"});
    }
    public boolean isSingleUse(PlayerMob player) {
        return false;
    }
}