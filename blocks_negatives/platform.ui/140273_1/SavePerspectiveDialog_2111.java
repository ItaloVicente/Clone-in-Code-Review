        Font font = parent.getFont();
        Composite composite = (Composite) super.createDialogArea(parent);

        Label descLabel = new Label(composite, SWT.WRAP);
        descLabel.setText(WorkbenchMessages.SavePerspectiveDialog_description);
        descLabel.setFont(parent.getFont());

        Label label = new Label(composite, SWT.NONE);
        GridData data = new GridData();
        data.heightHint = 8;
        label.setLayoutData(data);

        Composite nameGroup = new Composite(composite, SWT.NONE);
        nameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = layout.marginHeight = 0;
        nameGroup.setLayout(layout);

        label = new Label(nameGroup, SWT.NONE);
        label.setText(WorkbenchMessages.SavePerspective_name);
        label.setFont(font);

        text = new Text(nameGroup, SWT.BORDER);
        text.setFocus();
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = convertWidthInCharsToPixels(TEXT_WIDTH);
        text.setLayoutData(data);
        text.setFont(font);
        text.addModifyListener(this);

        label = new Label(composite, SWT.NONE);
        data = new GridData();
        data.heightHint = 5;
        label.setLayoutData(data);

        label = new Label(composite, SWT.NONE);
        label.setText(WorkbenchMessages.SavePerspective_existing);
        label.setFont(font);

        list = new TableViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER);
        list.setLabelProvider(new PerspectiveLabelProvider());
        list.setContentProvider(new PerspContentProvider());
        list.addFilter(new ActivityViewerFilter());
        list.setComparator(new ViewerComparator());
        list.setInput(perspReg);
        list.addSelectionChangedListener(this);
        list.getTable().setFont(font);

        Control ctrl = list.getControl();
        GridData spec = new GridData(GridData.FILL_BOTH);
        spec.widthHint = convertWidthInCharsToPixels(LIST_WIDTH);
        spec.heightHint = convertHeightInCharsToPixels(LIST_HEIGHT);
        ctrl.setLayoutData(spec);

        if (initialSelection != null) {
            StructuredSelection sel = new StructuredSelection(initialSelection);
            list.setSelection(sel, true);
        }
        text.selectAll();

        return composite;
    }

    /**
     * Returns the target name.
     *
     * @return the target name
     */
    public IPerspectiveDescriptor getPersp() {
        return persp;
    }

    /**
     * Returns the target name.
     *
     * @return the target name
     */
    public String getPerspName() {
        return perspName;
    }

    /**
     * The user has typed some text.
     */
    @Override
