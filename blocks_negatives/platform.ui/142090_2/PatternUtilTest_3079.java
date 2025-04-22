    /**
     * Tests if the quote function of ActivityPatternBinding works.
     */
    public static void testQuotePattern() {
    	assertTrue(PatternUtil.quotePattern("abcd").equals("\\Qabcd\\E"));
    	assertTrue(PatternUtil.quotePattern("Test\\Q").equals("\\QTest\\Q\\E"));
