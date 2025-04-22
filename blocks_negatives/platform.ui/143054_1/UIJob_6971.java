    /**
     * Convenience method to return a status for an exception.
     *
     * @param exception
     * @return IStatus an error status built from the exception
     * @see Job
     */
    public static IStatus errorStatus(Throwable exception) {
        return WorkbenchPlugin.getStatus(exception);
    }
