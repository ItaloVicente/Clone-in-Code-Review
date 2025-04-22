	protected void addDefaultWorkingSetConfiguration(Composite composite) {
		Composite line = new Composite(composite, SWT.NONE);
		line.setLayout(new GridLayout(2, false));
		this.useDefaultWorkingSetCheckbox = new Button(line, SWT.CHECK);
		this.useDefaultWorkingSetCheckbox
				.setText(WorkbenchMessages.WorkingSetSelectionDialog_defaultWorkingSet);
		this.defaultWorkingSetSelector = new ComboViewer(line);
		this.defaultWorkingSetSelector.setLabelProvider(new WorkingSetLabelProvider());
		this.defaultWorkingSetSelector.setContentProvider(new ArrayContentProvider());
		this.defaultWorkingSetSelector.addFilter(new WorkingSetFilter(null));
		IWorkingSet[] workingSets = PlatformUI.getWorkbench().getWorkingSetManager()
				.getWorkingSets();
		defaultWorkingSetSelector.setInput(workingSets);
		if (this.defaultWorkingSet != null) {
			this.defaultWorkingSetSelector.setSelection(new StructuredSelection(
					this.defaultWorkingSet), true);
		}
		this.useDefaultWorkingSetCheckbox.setSelection(this.useDefaultWorkingSet);
		this.defaultWorkingSetSelector.getControl().setEnabled(this.useDefaultWorkingSet);

		this.missingDefaultWorkingSetDecoration = new ControlDecoration(
				this.defaultWorkingSetSelector.getControl(), SWT.TOP | SWT.LEFT);
		this.missingDefaultWorkingSetDecoration.setImage(FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage());

		this.useDefaultWorkingSetCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbstractWorkingSetDialog.this.useDefaultWorkingSet = AbstractWorkingSetDialog.this.useDefaultWorkingSetCheckbox
						.getSelection();
				updateButtonAvailability();
			}
		});
		this.defaultWorkingSetSelector.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = AbstractWorkingSetDialog.this.defaultWorkingSetSelector
						.getSelection();
				if (selection.isEmpty()) {
					AbstractWorkingSetDialog.this.defaultWorkingSet = null;
				} else {
					AbstractWorkingSetDialog.this.defaultWorkingSet = (IWorkingSet) ((IStructuredSelection) selection)
							.getFirstElement();
				}
				updateButtonAvailability();
			}
		});
	}

