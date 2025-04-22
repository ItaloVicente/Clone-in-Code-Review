    @Test
    public void shouldRespectCASOnRemove() {
        String id = "removeWithCAS";
        JsonObject content = JsonObject.empty().put("hello", "world");
        final JsonDocument doc = JsonDocument.create(id, content);

        bucket().upsert(doc);
        JsonDocument response = bucket().get(id);
        assertEquals(content.getString("hello"), response.content().getString("hello"));

        try {
            bucket().remove(JsonDocument.create(id, null, 1231435L));
            assertTrue(false);
        } catch(CASMismatchException ex) {
            assertTrue(true);
        }

        response = bucket().get(id);
        assertEquals(content.getString("hello"), response.content().getString("hello"));

        JsonDocument removed = bucket().remove(response);
        assertEquals(removed.id(), response.id());
        assertNull(removed.content());
        assertTrue(removed.cas() != 0);
        assertNotEquals(response.cas(), removed.cas());

        assertNull(bucket().get(id));
    }

