        Font font = parent.getFont();

        Composite outerContainer = new Composite(parent, SWT.NONE);
        outerContainer.setLayout(new GridLayout());
        outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        outerContainer.setFont(font);

        Label messageLabel = new Label(outerContainer, SWT.NONE);
        messageLabel.setText(message);
        messageLabel.setFont(font);

        createViewer(outerContainer);
        layoutTopControl(viewer.getControl());

        restoreWidgetValues();

        setControl(outerContainer);
    }

    /**
     * Create a new viewer in the parent.
     *
     * @param parent the parent <code>Composite</code>.
     */
    private void createViewer(Composite parent) {
        Table table = new Table(parent, SWT.BORDER);
        table.setFont(parent.getFont());

        viewer = new TableViewer(table);
        viewer.setContentProvider(new WizardContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setComparator(new WorkbenchViewerComparator());
        viewer.addSelectionChangedListener(this);
        viewer.addDoubleClickListener(this);
        viewer.setInput(wizardElements);
    }

    /**
     * Returns an <code>IWizardNode</code> representing the specified
     * workbench wizard which has been selected by the user. <b>Subclasses
     * </b> must override this abstract implementation.
     *
     * @param element the wizard element that an <code>IWizardNode</code> is
     *            needed for
     * @return org.eclipse.jface.wizards.IWizardNode
     */
    protected abstract IWizardNode createWizardNode(
            WorkbenchWizardElement element);

    /**
     * An item in a viewer has been double-clicked.
     */
    @Override
