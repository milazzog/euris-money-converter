package it.euris.moneyconverter;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MoneyUnitUT {

    @Test
    public void test1(){
        Assertions.assertThat((new Pence(1)).getValue()).isEqualTo(1);
        Assertions.assertThat((new Pence(1)).getQuantumRelated()).isEqualTo(1);
        Assertions.assertThat((new Shilling(1)).getValue()).isEqualTo(1);
        Assertions.assertThat((new Shilling(1)).getQuantumRelated()).isEqualTo(12);
        Assertions.assertThat((new Pound(1)).getValue()).isEqualTo(1);
        Assertions.assertThat((new Pound(1)).getQuantumRelated()).isEqualTo(240);
    }

    @Test
    public void test2(){
        Assertions.assertThat((new Pence(1)).add(new Pence(1)).getValue()).isEqualTo(2);
        Assertions.assertThat((new Pence(1)).add(new Pence(1)).getReminder()).isEqualTo(0);
        Assertions.assertThat((new Pence(1)).add(new Shilling(1)).getValue()).isEqualTo(13);
        Assertions.assertThat((new Pence(1)).add(new Shilling(1)).getReminder()).isEqualTo(0);
        Assertions.assertThat((new Pence(1)).add(new Pound(1)).getValue()).isEqualTo(241);
        Assertions.assertThat((new Pence(1)).add(new Pound(1)).getReminder()).isEqualTo(0);

        Assertions.assertThat((new Shilling(1)).add(new Pence(1)).getValue()).isEqualTo(1);
        Assertions.assertThat((new Shilling(1)).add(new Pence(1)).getReminder()).isEqualTo(1);
        Assertions.assertThat((new Shilling(1)).add(new Shilling(1)).getValue()).isEqualTo(2);
        Assertions.assertThat((new Shilling(1)).add(new Shilling(1)).getReminder()).isEqualTo(0);
        Assertions.assertThat((new Shilling(1)).add(new Pound(1)).getValue()).isEqualTo(21);
        Assertions.assertThat((new Shilling(1)).add(new Pound(1)).getReminder()).isEqualTo(0);

        Assertions.assertThat((new Pound(1)).add(new Pence(1)).getValue()).isEqualTo(1);
        Assertions.assertThat((new Pound(1)).add(new Pence(1)).getReminder()).isEqualTo(1);
        Assertions.assertThat((new Pound(1)).add(new Shilling(1)).getValue()).isEqualTo(2);
        Assertions.assertThat((new Pound(1)).add(new Shilling(1)).getReminder()).isEqualTo(0);
        Assertions.assertThat((new Pound(1)).add(new Pound(1)).getValue()).isEqualTo(21);
        Assertions.assertThat((new Pound(1)).add(new Pound(1)).getReminder()).isEqualTo(0);
    }
}
