            return FAILED_VALUE;
        }

        if (value <= 0) {
            setErrorMessage(IDEWorkbenchMessages.FileHistory_mustBePositive);
            return FAILED_VALUE;
        }

        return value;
    }

    /**
     * Validate a text entry for a long field. Return the result if there are
     * no errors, otherwise return -1 and set the entry field error.
     * @param scale the scale (factor by which the value is multiplied when it is persisted)
     * @return long
     */
    private long validateLongTextEntry(Text text, long scale) {

        long value;

        try {
            String string = text.getText();
