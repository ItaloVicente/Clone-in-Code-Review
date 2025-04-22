    }

    /**
     * Specifies if sorting, filtering and folding is case sensitive.
     * @param ignoreCase
     */
    public void setIgnoreCase(boolean ignoreCase) {
        fIgnoreCase = ignoreCase;
    }

    /**
     * Returns if sorting, filtering and folding is case sensitive.
     * @return boolean
     */
    public boolean isCaseIgnored() {
        return fIgnoreCase;
    }

    /**
     * Specifies whether everything or nothing should be filtered on
     * empty filter string.
     * @param matchEmptyString boolean
     */
    public void setMatchEmptyString(boolean matchEmptyString) {
        fMatchEmptyString = matchEmptyString;
    }

    /**
     * Specifies if multiple selection is allowed.
     * @param multipleSelection
     */
    public void setMultipleSelection(boolean multipleSelection) {
        fIsMultipleSelection = multipleSelection;
    }

    /**
     * Specifies whether duplicate entries are displayed or not.
     * @param allowDuplicates
     */
    public void setAllowDuplicates(boolean allowDuplicates) {
        fAllowDuplicates = allowDuplicates;
    }

    /**
     * Sets the list size in unit of characters.
     * @param width  the width of the list.
     * @param height the height of the list.
     */
    public void setSize(int width, int height) {
        fWidth = width;
        fHeight = height;
    }

    /**
     * Sets the message to be displayed if the list is empty.
     * @param message the message to be displayed.
     */
    public void setEmptyListMessage(String message) {
        fEmptyListMessage = message;
    }

    /**
     * Sets the message to be displayed if the selection is empty.
     * @param message the message to be displayed.
     */
    public void setEmptySelectionMessage(String message) {
        fEmptySelectionMessage = message;
    }

    /**
     * Sets an optional validator to check if the selection is valid.
     * The validator is invoked whenever the selection changes.
     * @param validator the validator to validate the selection.
     */
    public void setValidator(ISelectionStatusValidator validator) {
        fValidator = validator;
    }

    /**
