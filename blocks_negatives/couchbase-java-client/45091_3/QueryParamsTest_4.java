
        Map<String, Integer> emptyVector = Collections.emptyMap();
        p.consistencyAtPlus(emptyVector);
        p.injectParams(actual);
        assertEquals(2, actual.size());
        assertEquals("at_plus", actual.getString("scan_consistency"));
        assertNotNull(actual.get("scan_vector"));
