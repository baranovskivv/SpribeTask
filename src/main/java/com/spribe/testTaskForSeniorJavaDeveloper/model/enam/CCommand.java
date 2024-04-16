package com.spribe.testTaskForSeniorJavaDeveloper.model.enam;

public enum CCommand {
    GET_CURRENCIES("https://openexchangerates.org/api/currencies.json"),

    GET_CURRENCY_RATES("https://openexchangerates.org/api/latest.json?app_id=");

    private final String url;

    CCommand(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
