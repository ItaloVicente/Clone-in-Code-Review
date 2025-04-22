			CheckboxTableViewer viewer = CheckboxTableViewer.newCheckList(composite,
					SWT.CHECK | SWT.BORDER | SWT.SINGLE);
			viewer.setContentProvider(new ActivityContentProvider());
			viewer.setLabelProvider(new ActivityLabelProvider(manager));
			viewer.setInput(activityIdsCopy);
			viewer.setCheckedElements(activityIdsCopy.toArray());
			viewer.addCheckStateListener(event -> {
				if (event.getChecked()) {
