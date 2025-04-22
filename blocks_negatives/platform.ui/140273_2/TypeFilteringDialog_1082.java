    /**
     *  Creates a type filtering dialog using the supplied entries. Set the
     * initial selections to those whose extensions match the preselections.
     * @param parentShell The shell to parent the dialog from.
     * @param preselections
     *            of String - a Collection of String to define the preselected
     *            types
     */
    public TypeFilteringDialog(Shell parentShell, Collection preselections) {
        super(parentShell);
        setTitle(WorkbenchMessages.TypesFiltering_title);
        this.initialSelections = preselections;
        setMessage(WorkbenchMessages.TypesFiltering_message);
