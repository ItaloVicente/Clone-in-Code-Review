    /**
     * Sets the image and message to be displayed on the status line.
     * <p>
     * This method replaces the current message but does not affect the
     * error message. That is, the error message, if set, will continue
     * to be displayed until it is cleared (set to <code>null</code>).
     * </p>
     *
     * @param image the image to use, or <code>null</code> for no image
     * @param message the message, or <code>null</code> for no message
     */
    public void setMessage(Image image, String message);
