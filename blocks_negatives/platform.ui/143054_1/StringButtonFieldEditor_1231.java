        return changeButton.getShell();
    }

    /**
     * Sets the text of the change button.
     *
     * @param text the new text
     */
    public void setChangeButtonText(String text) {
        Assert.isNotNull(text);
        changeButtonText = text;
        if (changeButton != null) {
