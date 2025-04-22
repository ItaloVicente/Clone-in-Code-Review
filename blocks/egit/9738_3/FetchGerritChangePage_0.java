		if(TasksUi.getTaskActivityManager().getActiveTask()!=null){
			baseBranchNameOnTaskButton = new Button(checkoutGroup, SWT.CHECK);
			baseBranchNameOnTaskButton.setText("Base branch name on active task"); //$NON-NLS-1$
			GridDataFactory.swtDefaults().span(2, 1).applyTo(baseBranchNameOnTaskButton);
			baseBranchNameOnTaskButton.setSelection(true);
			baseBranchNameOnTaskButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateBranchAndTag();
				}
			});
		}
