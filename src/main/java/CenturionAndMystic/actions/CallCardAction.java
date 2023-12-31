package CenturionAndMystic.actions;

import CenturionAndMystic.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CallCardAction extends AbstractGameAction {
    private final Predicate<AbstractCard> filter;
    private final Consumer<List<AbstractCard>> callback;
    private final List<AbstractCard> cardsMoved = new ArrayList<>();

    public CallCardAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, filter, c -> {});
    }

    public CallCardAction(int amount, Predicate<AbstractCard> filter, Consumer<List<AbstractCard>> callback) {
        this.amount = amount;
        this.filter = filter;
        this.callback = callback;
    }

    @Override
    public void update() {
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof CallMoreCardsPower) {
                amount += ((CallMoreCardsPower) p).bonusCalls();
            }
        }
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        boolean fromDiscardPile = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (filter.test(c)) {
                validCards.add(c);
            }
        }
        if (validCards.size() == 0) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (filter.test(c)) {
                    validCards.add(c);
                }
            }
            fromDiscardPile = true;
        }
        int cardsGot = 0;
        while (validCards.size() != 0 && AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE && cardsGot < amount) {
            AbstractCard card = validCards.get(AbstractDungeon.cardRng.random(validCards.size()-1));
            if (fromDiscardPile) {
                AbstractDungeon.player.hand.moveToHand(card, AbstractDungeon.player.discardPile);
            } else {
                AbstractDungeon.player.hand.moveToHand(card, AbstractDungeon.player.drawPile);
            }
            cardsMoved.add(card);
            if (card instanceof OnCallThisCard) {
                ((OnCallThisCard) card).onCalled();
            }
            validCards.remove(card);
            cardsGot++;
        }
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof OnCallPower) {
                ((OnCallPower) p).onCall(cardsMoved);
            }
        }
        for (AbstractCard c : Wiz.getAllCardsInCardGroups(true, true)) {
            if (c instanceof OnCallOtherCard) {
                ((OnCallOtherCard) c).onCall(cardsMoved);
            }
        }
        callback.accept(cardsMoved);
        isDone = true;
    }

    public interface OnCallPower {
        void onCall(List<AbstractCard> calledCards);
    }

    public interface CallMoreCardsPower {
        int bonusCalls();
    }

    public interface OnCallThisCard {
        void onCalled();
    }

    public interface OnCallOtherCard {
        void onCall(List<AbstractCard> calledCards);
    }
}