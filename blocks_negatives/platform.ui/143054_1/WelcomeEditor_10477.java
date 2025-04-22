    }

    /**
     * Returns the current text.
     */
    protected StyledText getCurrentText() {
        return currentText;
    }

    /**
     * Returns the copy action.
     */
    protected WelcomeEditorCopyAction getCopyAction() {
        return copyAction;
    }

    /**
     * Finds the next link after the current selection.
     */
    private StyleRange findNextLink(StyledText text) {
        if (text == null) {
