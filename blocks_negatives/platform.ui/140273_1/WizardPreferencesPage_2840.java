     * Adds an entry to a history, while taking care of duplicate history items
     * and excessively long histories.  The assumption is made that all histories
     * should be of length <code>WizardDataTransferPage.COMBO_HISTORY_LENGTH</code>.
     *
     * @param history the current history
     * @param newEntry the entry to add to the history
     */
    protected String[] addToHistory(String[] history, String newEntry) {
        java.util.ArrayList l = new java.util.ArrayList(Arrays.asList(history));
        addToHistory(l, newEntry);
        String[] r = new String[l.size()];
        l.toArray(r);
        return r;
    }

    /**
     * Adds an entry to a history, while taking care of duplicate history items
     * and excessively long histories.  The assumption is made that all histories
     * should be of length <code>WizardDataTransferPage.COMBO_HISTORY_LENGTH</code>.
     *
     * @param history the current history
     * @param newEntry the entry to add to the history
     */
    protected void addToHistory(List history, String newEntry) {
        history.remove(newEntry);
        history.add(0, newEntry);

        if (history.size() > COMBO_HISTORY_LENGTH) {
