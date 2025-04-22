
    public enum Option {

        UPDATE_MIN_CHANGES("updateMinChanges"),

        REPLICA_UPDATE_MIN_CHANGES("replicaUpdateMinChanges");

        private final String alias;

        Option(String alias) {
            this.alias = alias;
        }

        public String alias() {
            return alias;
        }

        public static Option fromName(final String name) {
            if (name.equals(UPDATE_MIN_CHANGES.alias())) {
                return UPDATE_MIN_CHANGES;
            } else if (name.equals(REPLICA_UPDATE_MIN_CHANGES.alias())) {
                return REPLICA_UPDATE_MIN_CHANGES;
            } else {
                throw new IllegalArgumentException("Unknown name: " + name);
            }
        }
    }
