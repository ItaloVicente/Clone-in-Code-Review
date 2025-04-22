		selectButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				SimpleWorkingSetSelectionDialog dialog = new SimpleWorkingSetSelectionDialog(
						parent.getShell(), workingSetTypeIds,
						selectedWorkingSets, false);
				dialog
						.setMessage(WorkbenchMessages.WorkingSetGroup_WorkingSetSelection_message);

				if (dialog.open() == Window.OK) {
					IWorkingSet[] result = dialog.getSelection();
					if (result != null && result.length > 0) {
						selectedWorkingSets = result;
						PlatformUI.getWorkbench().getWorkingSetManager()
								.addRecentWorkingSet(result[0]);
					} else {
						selectedWorkingSets = EMPTY_WORKING_SET_ARRAY;
					}
					updateWorkingSetSelection();
