    @Test
    public void shouldConvertNumbers() {
        JsonArray arr = JsonArray.create().add(1L);

        assertEquals(new Double(1.0d), arr.getDouble(0));
        assertEquals(new Long(1L), arr.getLong(0));
        assertEquals(new Integer(1), arr.getInt(0));
    }

    @Test
    public void shouldConvertOverflowNumbers() {
        int maxValue = Integer.MAX_VALUE; //int max value is 2147483647
        long largeValue = maxValue + 3L;
        double largerThanIntMaxValue = largeValue + 0.56d;

        JsonArray arr = JsonArray.create().add(largerThanIntMaxValue);
        assertEquals(new Double(largerThanIntMaxValue), arr.getDouble(0));
        assertEquals(new Long(largeValue), arr.getLong(0));
        assertEquals(new Integer(maxValue), arr.getInt(0));
    }

