        Composite contents = (Composite) super.createDialogArea(parent);
        createMessageArea(contents);
        createFilterText(contents);
        createLabel(contents, fUpperListLabel);
        createFilteredList(contents);
        createLabel(contents, fLowerListLabel);
        createLowerList(contents);
        setListElements(fElements);
        List initialSelections = getInitialElementSelections();
        if (!initialSelections.isEmpty()) {
            Object element = initialSelections.get(0);
            setSelection(new Object[] { element });
            setLowerSelectedElement(element);
        }
        return contents;
    }

    /**
     * Creates a label if name was not <code>null</code>.
     *
     * @param parent
     *            the parent composite.
     * @param name
     *            the name of the label.
     * @return returns a label if a name was given, <code>null</code>
     *         otherwise.
     */
    protected Label createLabel(Composite parent, String name) {
        if (name == null) {
