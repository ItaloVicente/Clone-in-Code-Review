        newText.setFocus();
        newText.setCaretOffset(caretOffset);
        scrolledComposite.setOrigin(0, newText.getLocation().y);
    }

    /**
     * Finds the next text
     */
    private StyledText nextText(StyledText text) {
        int index = 0;
        if (text == null) {
