package it.euris.moneyconverter.units;

public interface MoneyUnit {

    int getValue();
    String getSymbol();
    int getQuantumRelated();


    MoneyUnit add(MoneyUnit other);
    MoneyUnit sub(MoneyUnit other);
    MoneyUnit mul(int val);
    MoneyUnit div(int val);
}
