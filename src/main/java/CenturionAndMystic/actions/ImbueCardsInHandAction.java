package CenturionAndMystic.actions;

import CenturionAndMystic.powers.interfaces.OnCreateInfusionPower;
import CenturionAndMystic.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Predicate;

public class ImbueCardsInHandAction extends ModifyCardsInHandAction {
    public ImbueCardsInHandAction(int amount, AbstractCardModifier mod) {
        this(amount, false, c -> true, mod);
    }

    public ImbueCardsInHandAction(int amount, boolean anyAmount, Predicate<AbstractCard> filter, AbstractCardModifier mod) {
        super(amount, anyAmount, filter, l -> {
            for (AbstractCard c : l) {
                doInfusion(c, mod);
            }
            infusionSFX();
        });
    }

    public static void doInfusion(AbstractCard c, AbstractCardModifier mod) {
        int times = 1;
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof OnCreateInfusionPower) {
                times += ((OnCreateInfusionPower) p).increaseTimes(c, mod);
            }
        }
        for (int i = 0 ; i < times ; i++) {
            CardModifierManager.addModifier(c, mod.makeCopy());
            //Wiz.att(new ApplyCardModifierAction(c, mod.makeCopy()));
        }
        c.superFlash();
    }

    public static void infusionSFX() {
        CardCrawlGame.sound.play("MONSTER_COLLECTOR_SUMMON", 0.2f);
        CardCrawlGame.sound.play("RELIC_DROP_MAGICAL", 0.2f);
    }
}
