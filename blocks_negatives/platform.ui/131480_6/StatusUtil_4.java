    /**
     * Creates a new Status based on the original status, but with a different message
     *
     * @param originalStatus
     * @param newMessage
     * @return
     */
    public static IStatus newStatus(IStatus originalStatus, String newMessage) {
        return new Status(originalStatus.getSeverity(),
                originalStatus.getPlugin(), originalStatus.getCode(), newMessage, originalStatus.getException());
    }
