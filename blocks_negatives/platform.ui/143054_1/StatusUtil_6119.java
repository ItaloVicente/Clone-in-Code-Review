    /**
     * Returns a localized message describing the given exception. If the given exception does not
     * have a localized message, this returns the string "An error occurred".
     *
     * @param exception
     * @return
     */
    public static String getLocalizedMessage(Throwable exception) {
        String message = exception.getLocalizedMessage();
