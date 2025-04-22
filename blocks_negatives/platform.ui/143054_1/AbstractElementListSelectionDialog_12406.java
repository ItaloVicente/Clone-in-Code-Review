    }

    /**
     * Gets the optional validator used to check if the selection is valid.
     * The validator is invoked whenever the selection changes.
     * @return the validator to validate the selection, or <code>null</code>
     * if no validator has been set.
     *
     * @since 3.5
     */
    protected ISelectionStatusValidator getValidator() {
        return fValidator;
    }
