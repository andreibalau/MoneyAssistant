package moneyassistant.expert.model;

import lombok.Getter;

/**
 * MoneyAssistant
 * Created by catalin on 30.11.2019
 */
@Getter
public enum Currency {

    RON("ron"),
    EUR("eur"),
    USD("usd");

    String value;

    Currency(String value) {
        this.value = value;
    }

    public static CharSequence[] getItems() {
        return new CharSequence[] { RON.getValue(), EUR.getValue(), USD.getValue() };
    }

}
