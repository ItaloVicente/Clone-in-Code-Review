    /**
     * Creates a new call history for an object.
     *
     * @param target the call history target.
     */
    public CallHistory(Object target) {
        methodList = new ArrayList<>();
        classType = target.getClass();
    }
