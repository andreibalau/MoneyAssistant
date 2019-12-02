package moneyassistant.expert.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@RequiredArgsConstructor
public enum Wallet {

    BANK("Bank"),
    CARD("Card"),
    CASH("Cash"),
    DEBIT_CARD("Debit card"),
    INSURANCE("Insurance"),
    INVESTMENTS("Investments"),
    LOAN("Loan"),
    OTHERS("Others"),
    SAVINGS("Savings");

    private final String value;

    public static String[] makeArrayFromValues() {
        return new String[] {
                BANK.value,
                CARD.value,
                CASH.value,
                DEBIT_CARD.value,
                INSURANCE.value,
                INVESTMENTS.value,
                LOAN.value,
                SAVINGS.value,
                OTHERS.value
        };
    }

    public static String valueOf(int position) {
        return Arrays.asList(makeArrayFromValues()).get(position);
    }

}
