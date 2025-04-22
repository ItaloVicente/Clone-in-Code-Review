    extends DialogCellEditor {

    /**
     * Creates a new Font dialog cell editor parented under the given control.
     * The cell editor value is <code>null</code> initially, and has no
     * validator.
     * 
     * @param parent
     *            the parent control
     */
    protected FontDialogCellEditor(Composite parent) {
        super(parent);
    }

    /**
     * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(Control)
     */
    protected Object openDialogBox(Control cellEditorWindow) {
        FontDialog ftDialog = new FontDialog(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell());

        String value = (String) getValue();

        if ((value != null) && (value.length() > 0)) {
            ftDialog.setFontList(new FontData[] {new FontData(value)});
        }
        FontData fData = ftDialog.open();

        if (fData != null) {
            value = fData.toString();
        }
        return value;
    }
