            return FAILED_VALUE;
        }

        if (value <= 0) {
            setErrorMessage(IDEWorkbenchMessages.FileHistory_mustBePositive);
            return FAILED_VALUE;
        }

        return value;
    }

    /**
     * Validate the maximum file states.
     * Return the value if successful, otherwise
     * return FAILED_VALUE.
     * Set the error message if it fails.
     * @return int
     */
    private int validateMaxFileStates() {
        int maxFileStates = validateIntegerTextEntry(this.maxStatesText);
        if (maxFileStates == FAILED_VALUE) {
