			IDialogSettings settings = getDialogSettings(DIALOG_SETTINGS_SECTION);
			SelectionListener updateEnablement = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateEnablement();
				}
			};

