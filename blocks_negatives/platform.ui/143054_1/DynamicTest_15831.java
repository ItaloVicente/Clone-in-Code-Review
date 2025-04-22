    /**
     * Test activity bindings.
     *
     */
    public void testActivityPatternBindings() {
        IActivity first_activity = activityManager
                .getActivity((String) activityManager.getDefinedActivityIds()
                        .toArray()[0]);
