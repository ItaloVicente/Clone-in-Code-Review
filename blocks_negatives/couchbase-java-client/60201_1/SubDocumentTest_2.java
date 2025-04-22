        assertNotNull(jsonObjectFragment.fragment());
        assertEquals(JsonObject.create().put("value", "original"), jsonObjectFragment.fragment());

        assertNotNull(mapFragment);
        assertNotNull(mapFragment.fragment());
        assertEquals(Collections.singletonMap("value", "original"), mapFragment.fragment());

        assertNotNull(subValueFragment);
        assertNotNull(subValueFragment.fragment());
        assertEquals("original", subValueFragment.fragment().value);
    }

    @Test
    public void testGetInWitTargetClass() {
        DocumentFragment<JsonObject> fragment = ctx.bucket().getIn(key, "sub", JsonObject.class);

        assertNotNull(fragment);
        assertNotNull(fragment.fragment());
        assertEquals("original", fragment.fragment().get("value"));
