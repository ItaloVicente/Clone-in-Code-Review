    }

    /**
     * Log the status to the default log.
     * @param status
     */
    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }

    /**
     * Get the decorator manager for the receiver
     * @return DecoratorManager the decorator manager
     * for the receiver.
     */
    public DecoratorManager getDecoratorManager() {
