package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.powers.InfuseHexPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Halt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Enforce extends AbstractMysticCard {
    public final static String ID = makeID(Enforce.class.getSimpleName());

    public Enforce() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new InfuseHexPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        //upgradeBaseCost(0);
        selfRetain = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Halt.ID;
    }
}