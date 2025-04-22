
            public List<Integer> markers() {
                return markers;
            }
        }
    }).map(new Func1<JsonObject, ViewRow>() {
        @Override
        public ViewRow call(JsonObject object) {
            return new ViewRow(object.getString("id"), object.getString("key"), object.get("value"));
