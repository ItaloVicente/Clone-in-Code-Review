			storeCheckbox
					.setSelection(Activator
							.getDefault()
							.getPreferenceStore()
							.getBoolean(
									UIPreferences.CLONE_WIZARD_STORE_SECURESTORE));
			storeCheckbox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Activator
							.getDefault()
							.getPreferenceStore()
							.setValue(
									UIPreferences.CLONE_WIZARD_STORE_SECURESTORE,
									storeCheckbox.getSelection());
				}
			});
