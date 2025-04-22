
    @Test
    public void shouldSupportBigInteger() throws Exception {
        BigInteger bigint = new BigInteger("12345678901234567890");
        JsonArray original = JsonArray.from(bigint);


        String encoded = original.toString();
        assertEquals("[12345678901234567890]", encoded);

        JsonArray decoded = JsonArray.fromJson(encoded);
        assertEquals(bigint, decoded.getBigInteger(0));
        assertTrue(decoded.getNumber(0) instanceof BigInteger);

    }

    @Test
    public void shouldSupportBigDecimalConverted() throws Exception {
        BigDecimal bigdec = new BigDecimal("1234.5678901234567890432423432324");
        JsonArray original = JsonArray.from(bigdec);


        String encoded = original.toString();
        assertEquals("[1234.5678901234567890432423432324]", encoded);

        JsonArray decoded = JsonArray.fromJson(encoded);
        assertEquals(
            new BigDecimal("1234.5678901234568911604583263397216796875"), decoded.getBigDecimal(0)
        );
        assertEquals(1234.567890123457, decoded.getDouble(0), 0);
        assertTrue(decoded.getNumber(0) instanceof Double);
    }
