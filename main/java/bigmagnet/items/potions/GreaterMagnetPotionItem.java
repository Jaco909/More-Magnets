package bigmagnet.items.potions;

import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class GreaterMagnetPotionItem extends SimplePotionItem {
    public GreaterMagnetPotionItem() {
        super(100, Rarity.COMMON, "greatermagnetpotion", 600, new String[]{"greatermagnetpot"});
        this.setItemCategory(ItemCategory.craftingManager, new String[]{"consumable", "utilitypotions"});
        this.overridePotion("magnetpotion");
    }
}