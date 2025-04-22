        if (maxFileStates > FILE_STATES_MAXIMUM) {
            setErrorMessage(NLS.bind(IDEWorkbenchMessages.FileHistory_aboveMaxEntries, String.valueOf(FILE_STATES_MAXIMUM)));
            return FAILED_VALUE;
        }

        return maxFileStates;
    }

    /**
     * Validate the maximum file state size.
     * Return the value if successful, otherwise
     * return FAILED_VALUE.
     * Set the error message if it fails.
     * @return long
     */
    private long validateMaxFileStateSize() {
        long maxFileStateSize = validateLongTextEntry(this.maxStateSizeText, MEGABYTES);
        if (maxFileStateSize == FAILED_VALUE) {
