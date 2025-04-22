    private static final Func1<JsonObject, String> DEFAULT_ID_GENERATOR = new Func1<JsonObject, String>() {
        @Override
        public String call(JsonObject jsonObject) {
            return UUID.randomUUID().toString();
        }
    };

