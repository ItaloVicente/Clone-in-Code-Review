        MultiLookupResult resultPayload = ctx.bucket().lookupIn(key, LookupSpec.exists("sub[1]"));
        assertNotNull(resultPayload);
        List<LookupResult> results = resultPayload.results();
        assertEquals(1, results.size());
        LookupResult result = results.get(0);
        assertFalse(result.isFatal());
        assertEquals(false, result.exists());
        assertEquals(false, result.value());
        assertNotEquals(ResponseStatus.SUCCESS, result.status());
