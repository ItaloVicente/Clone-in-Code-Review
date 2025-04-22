     *
     * @return the list control
     */
    public Combo getCombo() {
    	Assert.isNotNull(combo);
        return combo;
    }

    /*
     * Do nothing -- combos only display the selected element, so there is no way
     * we can ensure that the given element is visible without changing the selection.
     * Method defined on StructuredViewer.
     */
    @Override
