        DocumentFragment<Lookup> results = ctx.bucket().lookupIn(key)
            .get("boolean")
            .get("sub")
            .exists("string")
            .exists("no")
            .doLookup();

        assertNotNull(results);
