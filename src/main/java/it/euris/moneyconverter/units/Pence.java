package it.euris.moneyconverter.units;

public class Pence extends MoneyUnit<Pence> {

    public Pence(int value) {
        super(value);
    }

    Pence(int v, boolean asRaw) {
        super(v, asRaw);
    }

    @Override
    protected MoneyUnitType getType() {
        return MoneyUnitType.PENCE;
    }

    @Override
    protected Pence doFlush(int r) {
        return new Pence(r, true);
    }

}
