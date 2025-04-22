    }

    /**
     * Indicates the end of a performance operation
     *
     * @param event The event id
     * @param blame An object that is responsible for the event that occurred,
     * or that uniquely describes the event that occurred
     * @param label The event label
     */
   	public static void end(int event, Object blame, String label) {
        if (debug[event]) {
            Long startTime = (Long) operations.remove(event + label);
            if (startTime == null) {
