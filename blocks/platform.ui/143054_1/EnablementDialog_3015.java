			text = new Label(composite, SWT.NONE);
			text.setText(strings.getProperty(WorkbenchTriggerPointAdvisor.PROCEED_SINGLE,
					RESOURCE_BUNDLE.getString(WorkbenchTriggerPointAdvisor.PROCEED_SINGLE)));
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			text.setFont(dialogFont);
		} else {
			text.setText(RESOURCE_BUNDLE.getString("requiresMulti")); //$NON-NLS-1$
			Set<String> activityIdsCopy = new HashSet<>(activityIds);
			CheckboxTableViewer viewer = CheckboxTableViewer.newCheckList(composite,
					SWT.CHECK | SWT.BORDER | SWT.SINGLE);
			viewer.setContentProvider(new ActivityContentProvider());
			viewer.setLabelProvider(new ActivityLabelProvider(manager));
			viewer.setInput(activityIdsCopy);
			viewer.setCheckedElements(activityIdsCopy.toArray());
			viewer.addCheckStateListener(event -> {
				if (event.getChecked()) {
					activitiesToEnable.add((String) event.getElement());
