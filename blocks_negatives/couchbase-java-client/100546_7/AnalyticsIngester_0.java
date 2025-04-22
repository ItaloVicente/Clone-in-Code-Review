        Func1<JsonObject, String> idGenerator = new Func1<JsonObject, String>() {
            @Override
            public String call(JsonObject jsonObject) {
                return UUID.randomUUID().toString();
            }
        };
