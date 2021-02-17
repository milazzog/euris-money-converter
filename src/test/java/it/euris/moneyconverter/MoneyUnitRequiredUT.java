package it.euris.moneyconverter;

import it.euris.moneyconverter.units.UkMoneyAmountBefore70;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MoneyUnitRequiredUT {

    /**
     * La somma e la sottrazione devono rispettivamente essere in grado di sommare e sottrarre due prezzi
     * Esempio somma: 5p 17s 8d + 3p 4s 10d= 9p 2s 6d
     */
    @Test
    public void testSum() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("5p 17s 8d");
        UkMoneyAmountBefore70 v2 = UkMoneyAmountBefore70.fromString("3p 4s 10d");
        Assertions.assertThat(v1.toString()).isEqualTo("5p 17s 8d");
        Assertions.assertThat(v2.toString()).isEqualTo("3p 4s 10d");
        UkMoneyAmountBefore70 res = v1.add(v2);
        Assertions.assertThat(res.toString()).isEqualTo("9p 2s 6d");
    }

    /**
     * Esempio sottrazione: 5p 17s 8d - 3p 4s 10d= 2p 12s 10d
     */
    @Test
    public void testSub1() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("5p 17s 8d");
        UkMoneyAmountBefore70 v2 = UkMoneyAmountBefore70.fromString("3p 4s 10d");
        Assertions.assertThat(v1.toString()).isEqualTo("5p 17s 8d");
        Assertions.assertThat(v2.toString()).isEqualTo("3p 4s 10d");
        UkMoneyAmountBefore70 res = v1.sub(v2);
        Assertions.assertThat(res.toString()).isEqualTo("2p 12s 10d");
    }

    /**
     * Se la sottrazione dà risultato negativo, il valore deve essere gestito e opportunamente rappresentato
     */
    @Test
    public void testSub2() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("1s");
        UkMoneyAmountBefore70 v2 = UkMoneyAmountBefore70.fromString("1p");
        Assertions.assertThat(v1.toString()).isEqualTo("1s 0d");
        Assertions.assertThat(v2.toString()).isEqualTo("1p 0s 0d");
        UkMoneyAmountBefore70 res = v1.sub(v2);
        Assertions.assertThat(res.toString()).isEqualTo("- 19s 0d");
    }

    /**
     * La moltiplicazione e divisione ricevono in input un prezzo e un intero (la moltiplicazione la divisione per valori decimali non sono previste)
     * Esempio moltiplicazione: 5p 17s 8d * 2 = 11p 15 s 4d
     */
    @Test
    public void testMul() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("5p 17s 8d");
        Assertions.assertThat(v1.toString()).isEqualTo("5p 17s 8d");
        UkMoneyAmountBefore70 res = v1.mul(2);
        Assertions.assertThat(res.toString()).isEqualTo("11p 15s 4d");
    }

    /**
     * Esempio divisione 5p 17s 8d / 3 = 1p 19s 2d (2p) (avanzano 2 penny - nell’output indicare il resto con racchiuso fra parentesi)
     */
    @Test
    public void testDiv1() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("5p 17s 8d");
        Assertions.assertThat(v1.toString()).isEqualTo("5p 17s 8d");
        UkMoneyAmountBefore70 res = v1.div(3);
        // ->   ERRATA ASSERZIONE => Assertions.assertThat(res.toString()).isEqualTo("1p 19s 2d (2p)");
        //          il risultato richiesto e' errato -> non possono mai avanzare 2 pound... infati si fa riferimento a penny che ha simbolo 'd'
        Assertions.assertThat(res.toString()).isEqualTo("1p 19s 2d (2d)");
    }

    /**
     * Altro esempio divisione 18p 16s 1d / 15 = 1p 5s 0d (1s 1d) (avanzano 1 scellino e 1 penny - nell’output indicare il resto con racchiuso fra parentesi)
     */
    @Test
    public void testDiv2() {
        UkMoneyAmountBefore70 v1 = UkMoneyAmountBefore70.fromString("18p 16s 1d");
        Assertions.assertThat(v1.toString()).isEqualTo("18p 16s 1d");
        UkMoneyAmountBefore70 res = v1.div(15);
        Assertions.assertThat(res.toString()).isEqualTo("1p 5s 0d (1s 1d)");
    }
}
