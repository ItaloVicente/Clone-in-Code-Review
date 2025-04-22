package com.couchbase.client.java.cluster;

public enum AuthDomain {
    LOCAL("local"),
    EXTERNAL("external");

    private final String alias;

    AuthDomain(String alias) {
        this.alias = alias;
    }

    public String alias() {
        return alias;
    }

    public static AuthDomain fromAlias(final String alias) {
        if (alias.equalsIgnoreCase("local")) {
            return LOCAL;
        } else if (alias.equalsIgnoreCase("external")) {
            return EXTERNAL;
        } else {
            throw new IllegalStateException("unknown alias:" + alias);
        }
    }
}
