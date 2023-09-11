package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.cardmods.GainBlockMod;
import CenturionAndMystic.patches.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CoilPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(CoilPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CoilPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("channel");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public float modifyBlock(float blockAmount) {
        return blockAmount + amount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (type == DamageInfo.DamageType.NORMAL && card.hasTag(CustomTags.CAM_POISED)) {
            damage += amount;
        }
        return damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.baseBlock > -1 && !(card instanceof RitualDagger)) || CardModifierManager.hasModifier(card, GainBlockMod.ID)) {
            this.flash();
            addToBot(new ReducePowerAction(owner, owner, this, amount));
        }
    }
}