        Object typedValue = value;
        boolean oldValidState = isValueValid();
        boolean newValidState = isCorrect(typedValue);
        if (!newValidState) {
            setErrorMessage(MessageFormat.format(getErrorMessage(), value));
        }
        valueChanged(oldValidState, newValidState);
    }

    /**
     * Since a text editor field is scrollable we don't
     * set a minimumSize.
     */
    @Override
