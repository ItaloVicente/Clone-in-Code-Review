    /**
     * Sets the message and image to the given status.
     * <code>null</code> is a valid argument and will set the empty text and no image
     */
    public void setErrorStatus(IStatus status) {
        if (status != null) {
            String message = status.getMessage();
            if (message != null && message.length() > 0) {
                setText(message);
                setImage(findImage(status));
                return;
            }
        }
