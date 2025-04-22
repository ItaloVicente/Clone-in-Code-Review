    return core
        .<ViewQueryResponse>send(request)
        .flatMap(new ViewQueryMapper(converters))
        .map(new Func1<JsonObject, ViewResult>() {
            @Override
            public ViewResult call(JsonObject object) {
                return new ViewResult(object.getString("id"), object.get("key"), object.get("value"));
            }
        }
    );
