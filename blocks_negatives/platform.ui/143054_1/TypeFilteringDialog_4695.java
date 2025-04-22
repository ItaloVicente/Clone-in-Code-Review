    /**
     * Creates a type filtering dialog using the supplied entries. Set the
     * initial selections to those whose extensions match the preselections.
     *
     * @param parentShell The shell to parent the dialog from.
     * @param preselections
     *            of String - a Collection of String to define the preselected
     *            types
     * @param filterText -
     *            the title of the text entry field for other extensions.
     */
    public TypeFilteringDialog(Shell parentShell, Collection preselections,
            String filterText) {
        this(parentShell, preselections);
        this.filterTitle = filterText;
    }
