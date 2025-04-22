    /**
     * Test sizes of what has been read.
     *
     */
    public void testSizes() {
        assertTrue(activityManager.getDefinedCategoryIds().size() == 6);
        assertTrue(activityManager.getDefinedActivityIds().size() == 18);
        assertTrue(activityManager.getEnabledActivityIds().size() == 3);
    }
