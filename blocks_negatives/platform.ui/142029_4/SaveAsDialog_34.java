    /**
     * Creates a new Save As dialog for no specific file.
     *
     * @param parentShell the parent shell
     */
    public SaveAsDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.SHEET);
    }
