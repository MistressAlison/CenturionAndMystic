package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.damageMods.AbstractDamageType;
import CenturionAndMystic.damageMods.VenomDamage;
import CenturionAndMystic.vfx.ColoredWrathParticleEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InfuseVenomPower extends AbstractInfusionPower {
    public static final String POWER_ID = MainModfile.makeID(InfuseVenomPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public InfuseVenomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.loadRegion("corruption");
        updateDescription();
    }

    @Override
    AbstractDamageType getDamageType() {
        return new VenomDamage(false, amount);
    }

    @Override
    void doVFX() {
        AbstractDungeon.effectsQueue.add(new ColoredWrathParticleEffect(Color.PURPLE));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (int)(100*amount* AbstractDamageType.PER_STACK) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new InfuseVenomPower(owner, amount);
    }
}