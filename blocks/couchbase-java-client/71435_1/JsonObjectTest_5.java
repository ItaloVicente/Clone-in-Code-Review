    @Test
    public void shouldSupportBigInteger() throws Exception {
        BigInteger bigint = new BigInteger("12345678901234567890");
        JsonObject original = JsonObject
            .create()
            .put("value", bigint);


        String encoded = original.toString();
        assertEquals("{\"value\":12345678901234567890}", encoded);

        JsonObject decoded = JsonObject.fromJson(encoded);
        assertEquals(bigint, decoded.getBigInteger("value"));
        assertTrue(decoded.getNumber("value") instanceof BigInteger);
    }

    @Test
    public void shouldSupportBigDecimalConverted() throws Exception {
        BigDecimal bigdec = new BigDecimal("1234.5678901234567890432423432324");
        JsonObject original = JsonObject
            .create()
            .put("value", bigdec);


        String encoded = original.toString();
        assertEquals("{\"value\":1234.5678901234567890432423432324}", encoded);

        JsonObject decoded = JsonObject.fromJson(encoded);
        assertEquals(
            new BigDecimal("1234.5678901234568911604583263397216796875"),
            decoded.getBigDecimal("value")
        );
        assertEquals(1234.567890123457, decoded.getDouble("value"), 0);
        assertTrue(decoded.getNumber("value") instanceof Double);
    }

