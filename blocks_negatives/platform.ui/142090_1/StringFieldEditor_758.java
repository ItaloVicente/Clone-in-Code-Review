        isValid = checkState();
    }

    /**
     * Sets whether the empty string is a valid value or not.
     *
     * @param b <code>true</code> if the empty string is allowed,
     *  and <code>false</code> if it is considered invalid
     */
    public void setEmptyStringAllowed(boolean b) {
        emptyStringAllowed = b;
    }

    /**
     * Sets the error message that will be displayed when and if
     * an error occurs.
     *
     * @param message the error message
     */
    public void setErrorMessage(String message) {
        errorMessage = message;
    }

    @Override
