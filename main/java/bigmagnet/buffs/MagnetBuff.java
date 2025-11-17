package bigmagnet.buffs;

import bigmagnet.BigMagnet;
import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;

import java.util.concurrent.atomic.AtomicBoolean;

public class MagnetBuff extends TrinketBuff {
    public MagnetBuff() {
        this.canCancel = false;
        this.isImportant = false;
        this.isVisible = false;
        this.isPassive = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ITEM_PICKUP_RANGE, 1.0F);
        buff.setStacks(BigMagnet.settingsGetter.getInt("magnetRadius"),9999,buff.owner);
    }
    public void clientTick(ActiveBuff buff) {
        if (BigMagnet.settingsGetter.getInt("magnetRadius") != buff.getStacks()){
            //buff.setModifier(BuffModifiers.ITEM_PICKUP_RANGE, BigMagnet.settingsGetter.getInt("magnetRadius")*1.0F);
            buff.setStacks(BigMagnet.settingsGetter.getInt("magnetRadius"),9999,buff.owner);
            buff.owner.buffManager.updateBuffs();
            buff.owner.buffManager.forceUpdateBuffs();
            buff.forceManagerUpdate();
        }
        buff.setDurationLeftSeconds(9999);
    }
    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = new ListGameTooltips(this.getDisplayName());
        tooltips.add(Localization.translate("buff", "supermagnettrinket","value",BigMagnet.settingsGetter.getInt("magnetRadius")));
        return tooltips;
    }
    public boolean overridesStackDuration() {
        return true;
    }
    public int getRemainingStacksDuration(ActiveBuff buff, AtomicBoolean sendUpdatePacket) {
        return 9999;
    }
}