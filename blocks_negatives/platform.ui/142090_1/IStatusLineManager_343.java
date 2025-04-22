    /**
     * Sets the image and error message to be displayed on the status line.
     * <p>
     * An error message overrides the current message until the error
     * message is cleared (set to <code>null</code>).
     * </p>
     *
     * @param image the image to use, or <code>null</code> for no image
     * @param message the error message, or <code>null</code> to clear
     * 		the current error message.
     */
    public void setErrorMessage(Image image, String message);
