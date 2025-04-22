        activityManager.setEnabledActivityIds(copySet);
        compareSet = activityManager.getEnabledActivityIds();
        assertTrue(compareSet.size() == copySet.size());
        copySet.remove(activityManager.getDefinedActivityIds().toArray()[0]);
        activityManager.setEnabledActivityIds(copySet);
        compareSet = activityManager.getEnabledActivityIds();
        assertTrue(compareSet.size() == copySet.size());
    }

    /**
     * Test the identifier listener.
     *
     */
    public void testIdentifiersListener() {
        final IIdentifier enabledIdentifier = activityManager
        assertTrue(enabledIdentifier.isEnabled());
