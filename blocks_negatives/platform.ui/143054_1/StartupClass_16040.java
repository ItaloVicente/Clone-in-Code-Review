    }

    public static boolean getEarlyStartupCalled() {
        return earlyStartupCalled;
    }

    public static boolean getEarlyStartupCompleted() {
        return earlyStartupCompleted;
    }

    /**
     * Reset the flags.
     */
    public static void reset() {
        earlyStartupCalled = false;
        earlyStartupCompleted = false;
    }
