    @Test
    public void shouldHaveSignature() {
        Query query = Query.simple("SELECT * FROM `" + bucketName() + "` LIMIT 3");
        QueryResult result = bucket().query(query);

        assertNotNull(result);
        assertTrue(result.parseSuccess());
        assertTrue(result.finalSuccess());
        assertNotNull(result.info());
        assertNotNull(result.signature());
        assertNotNull(result.allRows());
        assertNotNull(result.errors());
        assertFalse(result.allRows().isEmpty());
        assertTrue(result.errors().isEmpty());

        assertEquals(JsonObject.create().put("*", "*"), result.signature());
    }

