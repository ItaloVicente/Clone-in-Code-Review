    /**
     * Creates an property descriptor with the given id, display name, and list
     * of value labels to display in the combo box cell editor.
     *
     * @param id the id of the property
     * @param displayName the name to display for the property
     * @param labelsArray the labels to display in the combo box
     */
    public ComboBoxPropertyDescriptor(Object id, String displayName,
            String[] labelsArray) {
        super(id, displayName);
        labels = labelsArray;
    }
