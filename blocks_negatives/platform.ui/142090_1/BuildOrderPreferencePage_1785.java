                defaultsButtonSelected(defaultOrderButton.getSelection());
            }
        };
        this.defaultOrderButton.addSelectionListener(listener);

        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        this.defaultOrderButton.setLayoutData(gridData);
        this.defaultOrderButton.setFont(composite.getFont());
    }

    /**
     * Create the buttons used to manipulate the list. These Add, Remove and Move Up or Down
     * the list items.
     * @param composite the parent of the buttons
     * @param enableComposite - boolean that indicates if a composite should be enabled
     */
    private void createListButtons(Composite composite, boolean enableComposite) {

        Font font = composite.getFont();

        this.buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        this.buttonComposite.setLayout(layout);
        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.horizontalAlignment = GridData.FILL;
        this.buttonComposite.setLayoutData(gridData);
        this.buttonComposite.setFont(font);

        Button upButton = new Button(this.buttonComposite, SWT.CENTER
                | SWT.PUSH);
        upButton.setText(UP_LABEL);
        upButton.setEnabled(enableComposite);
        upButton.setFont(font);
        setButtonLayoutData(upButton);

        SelectionListener listener = new SelectionAdapter() {
            @Override
