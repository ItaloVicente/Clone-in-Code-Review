        DocumentFragment<Lookup> results = ctx.bucket()
                .lookupIn(key)
                .get("sub")
                .get("sub[1]")
                .get("badPath")
                .doLookup();

        assertNotNull(results);
