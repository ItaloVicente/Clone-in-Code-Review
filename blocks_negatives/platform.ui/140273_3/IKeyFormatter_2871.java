    /**
     * Formats an individual key into a human readable format. This uses an
     * internationalization resource bundle to look up the key. This does not
     * do any platform-specific formatting (e.g., Carbon's command character).
     *
     * @param key
     *            The key to format; must not be <code>null</code>.
     * @return The key formatted as a string; should not be <code>null</code>.
     */
    String format(Key key);
