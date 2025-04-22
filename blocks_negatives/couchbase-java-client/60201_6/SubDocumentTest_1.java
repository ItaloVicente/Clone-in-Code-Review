        DocumentFragment<Object> objectFragment = ctx.bucket().getIn(key, "sub", Object.class);
        DocumentFragment<Object> intFragment = ctx.bucket().getIn(key, "int", Object.class);
        DocumentFragment<Object> stringFragment = ctx.bucket().getIn(key, "string", Object.class);
        DocumentFragment<Object> arrayFragment = ctx.bucket().getIn(key, "array", Object.class);
        DocumentFragment<Object> booleanFragment = ctx.bucket().getIn(key, "boolean", Object.class);
        DocumentFragment<JsonObject> jsonObjectFragment = ctx.bucket().getIn(key, "sub", JsonObject.class);
        DocumentFragment<Map> mapFragment = ctx.bucket().getIn(key, "sub", Map.class);
        DocumentFragment<SubValue> subValueFragment = ctx.bucket().getIn(key, "sub", SubValue.class);
