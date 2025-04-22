        LookupResult result = results.get(0);
        assertNotNull(result.value());
        assertTrue(result.exists());
        assertEquals(ResponseStatus.SUCCESS, result.status());
        assertEquals(testJson.getObject("sub"), result.value());
        assertFalse(result.isFatal());
