        return false;
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

        GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
        button.setLayoutData(buttonData);

        button.setData(id);
        button.setText(label);
        button.setFont(parent.getFont());

        if (defaultButton) {
            Shell shell = parent.getShell();
            if (shell != null) {
                shell.setDefaultButton(button);
            }
            button.setFocus();
        }
        button.setFont(parent.getFont());
        setButtonLayoutData(button);
        return button;
    }

    /**
     * Creates the buttons for selecting specific types or selecting all or none of the
     * elements.
     *
     * @param parent the parent control
     */
    protected final void createButtonsGroup(Composite parent) {

        Font font = parent.getFont();

        Composite buttonComposite = new Composite(parent, SWT.NONE);
        buttonComposite.setFont(parent.getFont());

        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        layout.makeColumnsEqualWidth = true;
        buttonComposite.setLayout(layout);
        buttonComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));

        Button selectTypesButton = createButton(buttonComposite,
                IDialogConstants.SELECT_TYPES_ID, SELECT_TYPES_TITLE, false);

        SelectionListener listener = new SelectionAdapter() {
            @Override
