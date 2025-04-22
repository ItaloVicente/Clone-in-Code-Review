
		Composite radioGroup = new Composite(composite, SWT.NULL);
		GridData data = new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1);
		radioGroup.setLayoutData(data);
		enableMru = new RadioGroupFieldEditor(StackRenderer.MRU_KEY,
				WorkbenchMessages.ViewsPreference_tabPlacement, 1,
				new String[][] {
						new String[] { WorkbenchMessages.ViewsPreference_enableOpeningOrder, Boolean.FALSE.toString() },
						new String[] { WorkbenchMessages.ViewsPreference_enableMRU, Boolean.TRUE.toString() } },
				radioGroup, true);
		ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.eclipse.e4.ui.workbench.renderers.swt"); //$NON-NLS-1$
		store.setDefault(StackRenderer.MRU_KEY, Boolean.toString(getDefaultMRUValue()));
		enableMru.setPreferenceStore(store);
		enableMru.fillIntoGrid(radioGroup, 2);
		enableMru.load();
