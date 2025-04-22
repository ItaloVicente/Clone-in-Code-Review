        imageDescriptor = desc;
        if (image != null) {
            image.dispose();
            image = null;
        }
    }

    /**
     * Sets or clears the message for this page.
     * <p>
     * This is a shortcut for <code>setMessage(newMesasge, NONE)</code>
     * </p>
     *
     * @param newMessage
     *            the message, or <code>null</code> to clear the message
     */
    public void setMessage(String newMessage) {
        setMessage(newMessage, NONE);
    }

    /**
     * Sets the message for this page with an indication of what type of message
     * it is.
     * <p>
     * The valid message types are one of <code>NONE</code>,
     * <code>INFORMATION</code>,<code>WARNING</code>, or
     * <code>ERROR</code>.
     * </p>
     * <p>
     * Note that for backward compatibility, a message of type
     * <code>ERROR</code> is different than an error message (set using
     * <code>setErrorMessage</code>). An error message overrides the current
     * message until the error message is cleared. This method replaces the
     * current message and does not affect the error message.
     * </p>
     *
     * @param newMessage
     *            the message, or <code>null</code> to clear the message
     * @param newType
     *            the message type
     * @since 2.0
     */
    public void setMessage(String newMessage, int newType) {
        message = newMessage;
        messageType = newType;
    }

    /**
     * The <code>DialogPage</code> implementation of this
     * <code>IDialogPage</code> method remembers the title in an internal
     * state variable. Subclasses may extend.
     */
    @Override
