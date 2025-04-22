    /**
     * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
     */
    public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new FontDialogCellEditor(parent);
        if (getValidator() != null)
            editor.setValidator(getValidator());
        return editor;
    }
