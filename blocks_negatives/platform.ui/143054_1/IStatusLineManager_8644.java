    /**
     * Sets the message text to be displayed on the status line.
     * The image on the status line is cleared.
     * <p>
     * This method replaces the current message but does not affect the
     * error message. That is, the error message, if set, will continue
     * to be displayed until it is cleared (set to <code>null</code>).
     * </p>
     *
     * @param message the message, or <code>null</code> for no message
     */
    public void setMessage(String message);
