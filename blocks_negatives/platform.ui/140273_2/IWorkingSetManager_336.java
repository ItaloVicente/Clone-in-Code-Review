    /**
     * Creates a working set selection dialog that lists all working
     * sets with the specified ids and allows the user to add, remove and
     * edit working sets with the specified ids.
     * The caller is responsible for opening the dialog with
     * <code>IWorkingSetSelectionDialog#open</code>, and subsequently
     * extracting the selected working sets using
     * <code>IWorkingSetSelectionDialog#getSelection</code>.
     *
     * @param parentShell the parent shell of the working set selection dialog
     * @param multi true= <code>IWorkingSetSelectionDialog#getSelection()</code>
     *  returns the working sets chosen in the dialog as an array of working set.
     *  false= <code>IWorkingSetSelectionDialog#getSelection()</code> returns
     *  an array having a single aggregate working set of all working sets
     *  selected in the dialog.
     * @param workingsSetIds a list of working set ids which are valid workings sets
     *  to be selected, created, removed or edited, or <code>null</code> if all currently
     *  available working set types are valid
     * @return a working set selection dialog
     * @since 3.1
     */
    IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parentShell, boolean multi, String[] workingsSetIds);
