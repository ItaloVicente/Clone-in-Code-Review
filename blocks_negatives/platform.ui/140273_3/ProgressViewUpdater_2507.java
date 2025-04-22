        return singleton;
    }

    /**
     * Return whether or not there is a singleton for updates to avoid creating
     * extra listeners.
     *
     * @return boolean <code>true</code> if there is already
     * a singleton
     */
    static boolean hasSingleton() {
        return singleton != null;
    }

    static void clearSingleton() {
        if (singleton != null) {
