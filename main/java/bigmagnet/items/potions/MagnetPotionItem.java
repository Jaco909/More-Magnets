package bigmagnet.items.potions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MagnetPotionItem extends SimplePotionItem {
    public MagnetPotionItem() {
        super(100, Rarity.UNCOMMON, "magnetpotion", 600, new String[]{"magnetpot"});
        this.setItemCategory(ItemCategory.craftingManager, new String[]{"consumable", "utilitypotions"});
    }
}