
	protected void createPreferenceMode(Composite composite) {
		Composite res = new Composite(composite, SWT.NONE);
		res.setLayout(new GridLayout(2, false));
		createLabel(res, WorkbenchMessages.WorkbenchPreference_preferencePresentation);
		Combo combo = createCombo(res);
		preferenceFacadeViewer = new ComboViewer(combo);
		preferenceFacadeViewer.setContentProvider(new ArrayContentProvider());
		preferenceFacadeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				switch ((PREFERENCE_FACADE_MODE) element) {
				case DIALOG:
					return WorkbenchMessages.WorkbenchPreference_preferencePresentation_dialog;
				case EDITOR:
					return WorkbenchMessages.WorkbenchPreference_preferencePresentation_editor;
				}
				return super.getText(element);
			}
		});
		preferenceFacadeViewer.setInput(PREFERENCE_FACADE_MODE.values());
		preferenceFacadeViewer.setSelection(new StructuredSelection(preferenceMode));
		preferenceFacadeViewer.addSelectionChangedListener(event -> {
			preferenceMode = (PREFERENCE_FACADE_MODE) ((IStructuredSelection) preferenceFacadeViewer.getSelection())
					.getFirstElement();
		});
	}
