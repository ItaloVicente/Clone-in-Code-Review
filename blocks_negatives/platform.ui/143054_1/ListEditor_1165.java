        return addButton.getShell();
    }

    /**
     * Splits the given string into a list of strings.
     * This method is the converse of <code>createList</code>.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @param stringList the string
     * @return an array of <code>String</code>
     * @see #createList
     */
    protected abstract String[] parseString(String stringList);

    /**
     * Notifies that the Remove button has been pressed.
     */
    private void removePressed() {
        setPresentsDefaultValue(false);
        int index = list.getSelectionIndex();
        if (index >= 0) {
            list.remove(index);
