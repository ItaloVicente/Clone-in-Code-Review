        if (validateLongTextEntry(longevityText, DAY_LENGTH) == FAILED_VALUE) {
            setValid(false);
            return;
        }

        if (validateMaxFileStates() == FAILED_VALUE) {
            setValid(false);
            return;
        }

        if (validateMaxFileStateSize() == FAILED_VALUE) {
            setValid(false);
            return;
        }

        setValid(true);
        setErrorMessage(null);
    }

    /*
     * Create the contents control for the workspace file states.
     * @returns Control
     * @param parent Composite
     */
    @Override
