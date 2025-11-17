package bigmagnet.items;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.trinketItem.SimpleTrinketItem;

public class SuperMagnet extends SimpleTrinketItem {

    public SuperMagnet() {
        super(Item.Rarity.RARE, "supermagnettrinket", 500, null);
        this.addDisables(new String[]{"itemattractor", "toolbox", "magnetbelt"});
    }

    @Override
    public TrinketBuff[] getBuffs(InventoryItem inventoryItem) {
        return new TrinketBuff[]{(TrinketBuff) BuffRegistry.getBuff("supermagnettrinket")};
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "supermagnettip"), 400);
        return tooltips;
    }
}