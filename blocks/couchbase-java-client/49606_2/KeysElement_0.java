        return clauseType.n1ql + expression.toString();
    }

    public static enum ClauseType {

        JOIN_ON("ON KEYS "),
        USE_KEYSPACE("USE KEYS "),
        JOIN_ON_PRIMARY("ON PRIMARY KEYS "),
        USE_KEYSPACE_PRIMARY("USE PRIMARY KEYS ");

        private final String n1ql;

        ClauseType(String n1ql) {
            this.n1ql = n1ql;
        }

