    /**
     * Creates a new instance of the receiver.
     *
     * @param editPage the working set page that is going to
     * 	be used for editing a working set.
     */
    public WorkingSetEditWizard(IWorkingSetPage editPage) {
        super();
        workingSetEditPage = editPage;
        workingSetEditPage.setWizard(this);
        setWindowTitle(WorkbenchMessages.WorkingSetEditWizard_title);
    }
