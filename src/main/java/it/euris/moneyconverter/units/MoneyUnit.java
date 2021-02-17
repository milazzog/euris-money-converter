package it.euris.moneyconverter.units;

/**
 * classe base per funzionalita' comuni
 * @param <T> l'unita' di misura precedente
 */
public abstract class MoneyUnit<T extends MoneyUnit<?>> {

    protected int raw;

    public MoneyUnit(int value) {
        this(value, false);
    }

    MoneyUnit(int v, boolean asRaw) {
        this.raw = asRaw ? v : v * getQuantumRelated();
    }

    protected abstract MoneyUnitType getType();

    /**
     * @return il simbolo
     */
    public final String getSymbol(){
        return getType().getSymbol();
    };

    /**
     * @return il valore rispetto alla minima unita' di misura
     */
    public final int getQuantumRelated(){
        return getType().getQuantum();
    }

    /**
     * @return il valore
     */
    public final int getValue() {
        return raw / getQuantumRelated();
    }

    /**
     * Costruisce l'unita' di misura precedente in base al resto della quantita' corrente
     * @param r il
     * @return l'unita' di misura precedente
     */
    protected abstract T doFlush(int r);

    int getReminder() {
        return raw % getQuantumRelated();
    }

    int getRaw(){
        return raw;
    }

    T flush(){
        int r = getReminder();
        raw -= getReminder();
        return doFlush(r);
    }
}
