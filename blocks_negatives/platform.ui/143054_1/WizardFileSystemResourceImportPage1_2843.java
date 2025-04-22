    /**
     *	Creates an instance of this class
     */
    protected WizardFileSystemResourceImportPage1(String name,
            IWorkbench aWorkbench, IStructuredSelection selection) {
        super(name, selection);
    }

    /**
     *	Creates an instance of this class
     *
     * @param aWorkbench IWorkbench
     * @param selection IStructuredSelection
     */
    public WizardFileSystemResourceImportPage1(IWorkbench aWorkbench,
            IStructuredSelection selection) {
        this("fileSystemImportPage1", aWorkbench, selection);//$NON-NLS-1$
        setTitle(DataTransferMessages.DataTransfer_fileSystemTitle);
        setDescription(DataTransferMessages.FileImport_importFileSystem);
    }

    /**
     * Creates a new button with the given id.
     * <p>
     * The <code>Dialog</code> implementation of this framework method
     * creates a standard push button, registers for selection events
     * including button presses and registers
     * default buttons with its shell.
     * The button id is stored as the buttons client data.
     * Note that the parent's layout is assumed to be a GridLayout and
     * the number of columns in this layout is incremented.
     * Subclasses may override.
     * </p>
     *
     * @param parent the parent composite
     * @param id the id of the button (see
     *  <code>IDialogConstants.*_ID</code> constants
     *  for standard dialog button ids)
     * @param label the label from the button
     * @param defaultButton <code>true</code> if the button is to be the
     *   default button, and <code>false</code> otherwise
     */
    protected Button createButton(Composite parent, int id, String label,
            boolean defaultButton) {
        ((GridLayout) parent.getLayout()).numColumns++;

        Button button = new Button(parent, SWT.PUSH);
        button.setFont(parent.getFont());

        GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
        button.setLayoutData(buttonData);

        button.setData(id);
        button.setText(label);

        if (defaultButton) {
            Shell shell = parent.getShell();
            if (shell != null) {
                shell.setDefaultButton(button);
            }
            button.setFocus();
        }
        return button;
    }

    /**
     * Creates the buttons for selecting specific types or selecting all or none of the
     * elements.
     *
     * @param parent the parent control
     */
    protected final void createButtonsGroup(Composite parent) {
        Composite buttonComposite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        layout.makeColumnsEqualWidth = true;
        buttonComposite.setLayout(layout);
        buttonComposite.setFont(parent.getFont());
        GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
        buttonComposite.setLayoutData(buttonData);

        selectTypesButton = createButton(buttonComposite,
                IDialogConstants.SELECT_TYPES_ID, SELECT_TYPES_TITLE, false);

        SelectionListener listener = new SelectionAdapter() {
            @Override
