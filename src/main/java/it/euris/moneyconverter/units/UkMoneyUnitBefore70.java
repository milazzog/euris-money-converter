package it.euris.moneyconverter.units;

public class UkMoneyUnitBefore70 implements MoneyUnit{

    private int value;
    private MoneyUnit surplus;
    private MoneyUnit reminder;

    protected UkMoneyUnitBefore70(int value, MoneyUnit surplus, MoneyUnit reminder) {
        this.value = value;
        this.surplus = surplus;
        this.reminder = reminder;
    }

    protected abstract MoneyUnit next(int surplus);
    protected abstract MoneyUnit prev(int reminder);

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public MoneyUnit add(MoneyUnit other) {
        int raw1 = other.getValue() * other.getQuantumRelated();
        int raw2 = getValue() * getQuantumRelated();
        return build((raw1 + raw2) / getQuantumRelated());
    }
}
