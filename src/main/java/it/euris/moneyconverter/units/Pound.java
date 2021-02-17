package it.euris.moneyconverter.units;

public class Pound extends MoneyUnit<Shilling> {

    public Pound(int value) {
        super(value);
    }

    Pound(int v, boolean asRaw) {
        super(v, asRaw);
    }

    @Override
    protected MoneyUnitType getType() {
        return MoneyUnitType.POUND;
    }

    @Override
    protected Shilling doFlush(int r) {
        return new Shilling(r, true);
    }

}
