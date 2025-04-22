    /**
     * Creates a new wizard page.
     *
     * @param pageName the name of the page
     */
    protected WizardDataTransferPage(String pageName) {
        super(pageName);
    }

    /**
     * Adds an entry to a history, while taking care of duplicate history items
     * and excessively long histories.  The assumption is made that all histories
     * should be of length <code>WizardDataTransferPage.COMBO_HISTORY_LENGTH</code>.
     *
     * @param history the current history
     * @param newEntry the entry to add to the history
     */
    protected String[] addToHistory(String[] history, String newEntry) {
