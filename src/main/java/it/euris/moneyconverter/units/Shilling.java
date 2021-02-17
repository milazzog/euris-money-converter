package it.euris.moneyconverter.units;

public class Shilling extends MoneyUnit<Pence> {

    public Shilling(int value) {
        super(value);
    }

    Shilling(int v, boolean asRaw) {
        super(v, asRaw);
    }

    @Override
    protected MoneyUnitType getType() {
        return MoneyUnitType.SHILLING;
    }

    @Override
    protected Pence doFlush(int r) {
        return new Pence(r, true);
    }
}
