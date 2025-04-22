        Object objectFragment = ctx.bucket().lookupIn(key)
                .get("sub").doLookup().content(0);

        Object intFragment = ctx.bucket().lookupIn(key)
                .get("int").doLookup().content(0);

        Object stringFragment = ctx.bucket().lookupIn(key)
                .get("string").doLookup().content(0);

        Object arrayFragment = ctx.bucket().lookupIn(key)
                .get("array").doLookup().content(0);

        Object booleanFragment = ctx.bucket().lookupIn(key)
                .get("boolean").doLookup().content(0);

        JsonObject jsonObjectFragment = ctx.bucket().lookupIn(key)
                .get("sub").doLookup().content(0, JsonObject.class);
