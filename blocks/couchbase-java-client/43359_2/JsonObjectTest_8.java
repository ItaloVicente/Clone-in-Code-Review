    @Test
    public void shouldConvertNumbers() {
        JsonObject obj = JsonObject.create().put("number", 1L);

        assertEquals(new Double(1.0d), obj.getDouble("number"));
        assertEquals(new Long(1L), obj.getLong("number"));
        assertEquals(new Integer(1), obj.getInt("number"));
    }

    @Test
    public void shouldConvertOverflowNumbers() {
        int maxValue = Integer.MAX_VALUE; //int max value is 2147483647
        long largeValue = maxValue + 3L;
        double largerThanIntMaxValue = largeValue + 0.56d;

        JsonObject obj = JsonObject.create().put("number", largerThanIntMaxValue);
        assertEquals(new Double(largerThanIntMaxValue), obj.getDouble("number"));
        assertEquals(new Long(largeValue), obj.getLong("number"));
        assertEquals(new Integer(maxValue), obj.getInt("number"));
    }

    @Test
    public void shouldNotNullPointerOnGetNumber() {
        JsonObject obj = JsonObject.empty();

        assertNull(obj.getDouble("number"));
        assertNull(obj.getLong("number"));
        assertNull(obj.getInt("number"));
    }

