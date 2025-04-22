        assertTrue(manipulatedIdentifiers.size() == 2);
        listenerType = 1;
        assertTrue(listenerType == -1);
        manipulatedIdentifiers = activityManager.getIdentifier(
        assertTrue(manipulatedIdentifiers.size() == 1);
    }

    /**
     * Test the activity manager listener.
     *
     */
    public void testActivityManagerListener() {
