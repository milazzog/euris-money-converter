package it.euris.moneyconverter.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
enum MoneyUnitType {

    PENCE(1, "d", Pattern.compile("^(?i)([+-]*\\d+)\\h*d?$")){
        @Override
        MoneyUnit<?> fromString(String raw) {
            Matcher matcher = getPattern().matcher(raw);
            if(matcher.matches()){
                return new Pence(Integer.parseInt(matcher.group(1)));
            }
            return null;
        }
    },
    SHILLING(12, "s", Pattern.compile("^(?i)([+-]*\\d+)\\h*s?$")){
        @Override
        MoneyUnit<?> fromString(String raw) {
            Matcher matcher = getPattern().matcher(raw);
            if(matcher.matches()){
                return new Shilling(Integer.parseInt(matcher.group(1)));
            }
            return null;
        }
    },
    POUND(240, "p", Pattern.compile("^(?i)([+-]*\\d+)\\h*p?$")){
        @Override
        MoneyUnit<?> fromString(String raw) {
            Matcher matcher = getPattern().matcher(raw);
            if(matcher.matches()){
                return new Pound(Integer.parseInt(matcher.group(1)));
            }
            return null;
        }
    };

    private final int quantum;
    private final String symbol;
    private final Pattern pattern;

    abstract MoneyUnit<?> fromString(String raw);

    static final Pattern GENERIC_PATTERN = Pattern.compile("(?i)([-+]*\\d+)\\h*[pds]");
}
