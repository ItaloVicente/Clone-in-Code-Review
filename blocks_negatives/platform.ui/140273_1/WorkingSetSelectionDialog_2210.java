    /**
     * Creates a working set selection dialog.
     *
     * @param parentShell the parent shell
     * @param multi decides how the results are returned with
     *  <code>WorkingSetSelectionDialog#getSelection()</code> or
     *  <code>WorkingSetSelectionDialog#getResult()</code>. true= working sets
     *  chosen in the dialog are returned as an array of working set.false= returns
     *  an array having a single aggregate working set of all working sets selected
     *  in the dialog.
     * @param workingSetIds a list of working set ids which are valid workings sets
     *  to be selected, created, removed or edited, or <code>null</code> if all currently
     *  available working set types are valid
     */
    public WorkingSetSelectionDialog(Shell parentShell, boolean multi, String[] workingSetIds) {
        super(parentShell, workingSetIds, true);
        initWorkbenchWindow();

        contentProvider = new ArrayContentProvider();
        labelProvider = new WorkingSetLabelProvider();
        multiSelect = multi;
        if (multiSelect) {
            setTitle(WorkbenchMessages.WorkingSetSelectionDialog_title_multiSelect);
            setMessage(WorkbenchMessages.WorkingSetSelectionDialog_message_multiSelect);
        } else {
            setTitle(WorkbenchMessages.WorkingSetSelectionDialog_title);
            setMessage(WorkbenchMessages.WorkingSetSelectionDialog_message);
        }

    }

    /**
