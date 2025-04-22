
			this.upButton = createButton(buttonComposite, ID_UP,
					WorkbenchMessages.WorkingSetSelectionDialog_upButton_label, false);
			this.upButton.setEnabled(false);
			this.upButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					upSelectedWorkingSet();
				}
			});

			this.downButton = createButton(buttonComposite, ID_DOWN,
					WorkbenchMessages.WorkingSetSelectionDialog_downButton_label, false);
			this.downButton.setEnabled(false);
			this.downButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					downSelectedWorkingSet();
				}
			});
