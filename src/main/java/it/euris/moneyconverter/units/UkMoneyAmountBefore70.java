package it.euris.moneyconverter.units;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class UkMoneyAmountBefore70 {

    private MoneyAmountInfo value;
    private MoneyAmountInfo reminder;

    /**
     * Partendo da una stringa di singoli importi ne parsa il contenuto ignorando i token non corretti e
     * restituisce una {@link UkMoneyAmountBefore70}
     *
     * fromString("5p 17s 8d") -> 5p 17s 8d
     * fromString("5p -6p") -> -1p
     * fromString("5p -asdasd") -> 4p
     *
     * @param literal la stringa da parsare
     * @return
     */
    public static UkMoneyAmountBefore70 fromString(String literal) {
        Matcher m1 = MoneyUnitType.GENERIC_PATTERN.matcher(StringUtils.defaultIfBlank(literal, StringUtils.EMPTY));
        UkMoneyAmountBefore70 res = new UkMoneyAmountBefore70(new Pence(0));
        while (m1.find()) {
            String token = StringUtils.trim(m1.group());
            for (MoneyUnitType t : MoneyUnitType.values()) {
                MoneyUnit<?> moneyUnit = t.fromString(token);
                if (Objects.nonNull(moneyUnit)) {
                    res = res.add(moneyUnit);
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Costruisce un {@link UkMoneyAmountBefore70} partendo da una {@link MoneyUnit}
     * @param moneyUnit unita' tra {@link Pence}, {@link Shilling} e {@link Pound}
     */
    public UkMoneyAmountBefore70(MoneyUnit<?> moneyUnit) {

        value = new MoneyAmountInfo(
                Pence.class.isAssignableFrom(moneyUnit.getClass()) ? (Pence) moneyUnit : new Pence(0),
                Shilling.class.isAssignableFrom(moneyUnit.getClass()) ? (Shilling) moneyUnit : new Shilling(0),
                Pound.class.isAssignableFrom(moneyUnit.getClass()) ? (Pound) moneyUnit : new Pound(0));
        reminder = new MoneyAmountInfo(new Pence(0), new Shilling(0), new Pound(0));
    }

    protected UkMoneyAmountBefore70(Pence pence, Shilling shilling, Pound pound) {
        int raw = ObjectUtils.defaultIfNull(pence, new Pence(0)).getRaw() + ObjectUtils.defaultIfNull(shilling, new Shilling(0)).getRaw() + ObjectUtils.defaultIfNull(pound, new Pound(0)).getRaw();
        Pound _pound = new Pound(raw, true);
        Shilling _shilling = _pound.flush();
        Pence _pence = _shilling.flush();
        value = new MoneyAmountInfo(_pence, _shilling, _pound);
        reminder = new MoneyAmountInfo(new Pence(0), new Shilling(0), new Pound(0));
    }

    /**
     * aggiunge {@param other}
     * @param other l'importo
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 add(UkMoneyAmountBefore70 other) {
        return fromRaw(rawSum() + other.rawSum(), 0);
    }

    /**
     * aggiunge {@param other}
     * @param other l'importo
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 add(MoneyUnit<?> other) {
        return fromRaw(rawSum() + other.getRaw(), 0);
    }

    /**
     * sottrae {@param other}
     * @param other l'importo
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 sub(UkMoneyAmountBefore70 other) {
        return fromRaw(rawSum() - other.rawSum(), 0);
    }

    /**
     * sottrae {@param other}
     * @param other l'importo
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 sub(MoneyUnit<?> other) {
        return fromRaw(rawSum() - other.getRaw(), 0);
    }

    /**
     * moltiplica per {@param val}
     * @param val valore
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 mul(int val) {
        return fromRaw(rawSum() * val, 0);
    }

    /**
     * divide per {@param val}
     * @param val valore
     * @return un nuovo oggetto {@link UkMoneyAmountBefore70} contenente il risultato dell'operazione
     */
    public UkMoneyAmountBefore70 div(int val) {
        return fromRaw(rawSum() / val, rawSum() % val);
    }

    /**
     * @return il valore contenuto al netto del resto
     */
    public MoneyAmountInfo getValue() {
        return new MoneyAmountInfo(new Pence(value.getPence().getValue()), new Shilling(value.getShilling().getValue()), new Pound(value.getPound().getValue()));
    }

    /**
     * @return il valore del resto
     */
    public MoneyAmountInfo getReminder() {
        return new MoneyAmountInfo(new Pence(reminder.getPence().getValue()), new Shilling(reminder.getShilling().getValue()), new Pound(reminder.getPound().getValue()));
    }

    public String toString(){
        return reminder.isZero() ? value.toString() : String.format("%s (%s)", value.toString(), reminder.toString());
    }

    protected int rawSum() {
        return value.getPence().getRaw() + value.getShilling().getRaw() + value.getPound().getRaw();
    }

    protected UkMoneyAmountBefore70 fromRaw(int rawTotal, int rawReminder) {
        Pound pound = new Pound(rawTotal, true);
        Shilling shilling = pound.flush();
        Pence pence = shilling.flush();
        return (new UkMoneyAmountBefore70(pence, shilling, pound)).withReminder(rawReminder);
    }

    protected UkMoneyAmountBefore70 withReminder(int rawReminder) {
        Pound pound = new Pound(rawReminder, true);
        Shilling shilling = pound.flush();
        Pence pence = shilling.flush();
        this.reminder = new MoneyAmountInfo(pence, shilling, pound);
        return this;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MoneyAmountInfo {

        private final Pence pence;
        private final Shilling shilling;
        private final Pound pound;

        protected boolean isZero(){
            return Stream.of(pence, shilling, pound).map(MoneyUnit::getRaw).allMatch(i -> i == 0);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            if(!isZero()) {
                boolean negativeCongruent = Stream.of(pence, shilling, pound).map(MoneyUnit::getRaw).filter(i -> i != 0).allMatch(i -> i < 0);
                boolean positiveCongruent = Stream.of(pence, shilling, pound).map(MoneyUnit::getRaw).filter(i -> i != 0).allMatch(i -> i > 0);
                if (!negativeCongruent && !positiveCongruent) {
                    throw new IllegalArgumentException();
                }
                if (negativeCongruent) {
                    b.append("- ");
                }
            }
            boolean poundAdded = false;
            if (pound.getValue() != 0) {
                b.append(Math.abs(pound.getValue())).append(pound.getSymbol()).append(" ");
                poundAdded = true;
            }
            if (poundAdded || shilling.getValue() != 0) {
                b.append(Math.abs(shilling.getValue())).append(shilling.getSymbol()).append(" ");
            }
            b.append(Math.abs(pence.getValue())).append(pence.getSymbol());
            return b.toString();
        }
    }

}
