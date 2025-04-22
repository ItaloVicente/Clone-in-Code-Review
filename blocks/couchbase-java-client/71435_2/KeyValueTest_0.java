    @Test
    public void shouldStoreAndGetBigInteger() {
        BigInteger bigint = new BigInteger("12345678901234567890432423432324");
        JsonObject original = JsonObject
            .create()
            .put("value", bigint);

        bucket().upsert(JsonDocument.create("bigIntegerDoc", original));

        RawJsonDocument rawLoaded = bucket().get("bigIntegerDoc", RawJsonDocument.class);
        assertEquals("{\"value\":12345678901234567890432423432324}", rawLoaded.content());

        JsonDocument loaded = bucket().get("bigIntegerDoc");
        assertEquals(bigint, loaded.content().getBigInteger("value"));
    }

    @Test
    public void shouldStoreAndGetBigDecimal() {
        BigDecimal bigdec = new BigDecimal("12345.678901234567890432423432324");
        JsonObject original = JsonObject
            .create()
            .put("value", bigdec);

        bucket().upsert(JsonDocument.create("bigDecimalDoc", original));

        RawJsonDocument rawLoaded = bucket().get("bigDecimalDoc", RawJsonDocument.class);
        assertEquals("{\"value\":12345.678901234567890432423432324}", rawLoaded.content());

        JsonDocument loaded = bucket().get("bigDecimalDoc");
        assertEquals(
            new BigDecimal("12345.678901234567092615179717540740966796875"),
            loaded.content().getBigDecimal("value")
        );
        assertEquals(12345.678901234567, loaded.content().getDouble("value"), 0);
    }

