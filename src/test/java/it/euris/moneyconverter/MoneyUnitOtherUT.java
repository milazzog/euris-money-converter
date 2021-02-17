package it.euris.moneyconverter;

import it.euris.moneyconverter.units.Pence;
import it.euris.moneyconverter.units.Pound;
import it.euris.moneyconverter.units.Shilling;
import it.euris.moneyconverter.units.UkMoneyAmountBefore70;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Altri test non richiesti
 */
public class MoneyUnitOtherUT {

    @Test
    public void test_quantum() {
        Assertions.assertThat((new Pence(1)).getQuantumRelated()).isEqualTo(1);
        Assertions.assertThat((new Shilling(1)).getQuantumRelated()).isEqualTo(12);
        Assertions.assertThat((new Pound(1)).getQuantumRelated()).isEqualTo(240);
    }

    @Test
    public void testAdd_complex() {
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Shilling(1)).toString()).isEqualTo("1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).toString()).isEqualTo("1p 0s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Shilling(1)).add(new Pound(1)).toString()).isEqualTo("1p 1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pence(12)).toString()).isEqualTo("1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Shilling(20)).toString()).isEqualTo("1p 0s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).add(new UkMoneyAmountBefore70(new Pence(1)).add(new Shilling(1))).toString()).isEqualTo("1p 1s 2d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).add(new UkMoneyAmountBefore70(new Pence(12)).add(new Shilling(1))).toString()).isEqualTo("1p 2s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).add(new UkMoneyAmountBefore70(new Pence(480)).add(new Shilling(1))).toString()).isEqualTo("3p 1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).add(new UkMoneyAmountBefore70(new Pence(12)).add(new Shilling(20))).toString()).isEqualTo("2p 1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).add(new Pound(1)).add(new UkMoneyAmountBefore70(new Pence(480)).add(new Shilling(24))).toString()).isEqualTo("4p 4s 1d");
    }

    @Test
    public void testSub_complex() {
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).sub(new Pence(1)).toString()).isEqualTo("0d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).sub(new Shilling(1)).toString()).isEqualTo("- 11d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).sub(new Pound(1)).toString()).isEqualTo("- 19s 11d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Shilling(1)).sub(new Pence(1)).toString()).isEqualTo("11d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Shilling(1)).sub(new Shilling(1)).toString()).isEqualTo("0d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Shilling(1)).sub(new Pound(1)).toString()).isEqualTo("- 19s 0d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pound(1)).sub(new Pence(1)).toString()).isEqualTo("19s 11d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pound(1)).sub(new Shilling(1)).toString()).isEqualTo("19s 0d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pound(1)).sub(new Pound(1)).toString()).isEqualTo("0d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pound(1)).add(new Shilling(1)).sub(new UkMoneyAmountBefore70(new Shilling(-1)).add(new Pence(-1))).toString()).isEqualTo("1p 2s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pound(1)).add(new Shilling(1)).sub(new UkMoneyAmountBefore70(new Shilling(1)).add(new Pence(1))).toString()).isEqualTo("19s 11d");
    }

    @Test
    public void testMul_complex() {
        Assertions.assertThat(new UkMoneyAmountBefore70(new Pence(1)).mul(253).toString()).isEqualTo("1p 1s 1d");
        Assertions.assertThat(new UkMoneyAmountBefore70(new Shilling(1)).mul(21).toString()).isEqualTo("1p 1s 0d");
    }

    @Test
    public void testDiv_complex() {
        UkMoneyAmountBefore70 div1 = new UkMoneyAmountBefore70(new Shilling(1)).div(2);
        Assertions.assertThat(div1.toString()).isEqualTo("6d");

        UkMoneyAmountBefore70 div2 = new UkMoneyAmountBefore70(new Shilling(1)).div(5);
        Assertions.assertThat(div2.toString()).isEqualTo("2d (2d)");
        Assertions.assertThat(div2.getValue().toString()).isEqualTo("2d");
        Assertions.assertThat(div2.getReminder().toString()).isEqualTo("2d");
    }

    @Test
    public void test_parse() {
        UkMoneyAmountBefore70 e1 = UkMoneyAmountBefore70.fromString("-12p +13 p 13 d 13 s");
        Assertions.assertThat(e1.toString()).isEqualTo("1p 14s 1d");
        UkMoneyAmountBefore70 e2 = UkMoneyAmountBefore70.fromString("5p -asdasd");
        Assertions.assertThat(e2.toString()).isEqualTo("5p 0s 0d");

    }
}
