package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.HeavyPlatingPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.FeelNoPain;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Bide extends AbstractCenturionCard {
    public final static String ID = makeID(Bide.class.getSimpleName());

    public Bide() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Wound();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.makeInHand(new Wound());
        Wiz.applyToSelf(new HeavyPlatingPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return FeelNoPain.ID;
    }
}