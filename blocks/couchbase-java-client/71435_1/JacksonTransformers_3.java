
        private final boolean decimalForFloat;

        public AbstractJsonValueDeserializer() {
            decimalForFloat = Boolean.parseBoolean(
                System.getProperty("com.couchbase.json.decimalForFloat", "false")
            );
        }

