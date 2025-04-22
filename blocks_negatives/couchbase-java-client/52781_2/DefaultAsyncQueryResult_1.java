        this.info = info.map(new Func1<JsonObject, QueryMetrics>() {
            @Override
            public QueryMetrics call(JsonObject jsonObject) {
                return new QueryMetrics(jsonObject);
            }
        });
