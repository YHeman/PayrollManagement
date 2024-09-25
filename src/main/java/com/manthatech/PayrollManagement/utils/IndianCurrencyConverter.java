package com.manthatech.PayrollManagement.utils;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

public class IndianCurrencyConverter {
    private static final RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(new ULocale("en_IN"), RuleBasedNumberFormat.SPELLOUT);

    public static String convertToWords(double amount) {
        String words = rbnf.format(amount, "%spellout-numbering-verbose");
        words = words.replaceAll("(?i)lakh", "lakh");
        words = words.replaceAll("(?i)crore", "crore");
        return words.substring(0, 1).toUpperCase() + words.substring(1) + " Rupees only";
    }
}
