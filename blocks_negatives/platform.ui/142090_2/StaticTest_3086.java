    /**
     * Test the activity manager's content.
     *
     */
    public void testActivityManager() {
        assertTrue(activityManager.getDefinedCategoryIds().containsAll(
                categoryIds));
        assertTrue(activityManager.getDefinedActivityIds().containsAll(
                activityIds));
        for (int index = 1; index <= 4; index++) {
