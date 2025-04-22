        }
    }

    /**
     * Returns whether tracing of the given debug event is turned on.
     *
     * @param event The event id
     * @return <code>true</code> if tracing of this event is turned on,
     * and <code>false</code> otherwise.
     */
    public static boolean isDebugging(int event) {
        return debug[event];
    }

    /**
     * Indicates the start of a performance event
     *
     * @param event The event id
     * @param label The event label
     */
    public static void start(int event, String label) {
        if (debug[event]) {
