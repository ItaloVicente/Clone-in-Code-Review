				Menu menu = new Menu(dropDownBar);
				MenuItem preferencesItem = new MenuItem(menu, SWT.PUSH);
				preferencesItem.setText(UIText.CommitDialog_ConfigureLink);
				preferencesItem.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e1) {
						PreferenceDialog dialog = PreferencesUtil
								.createPreferenceDialogOn(
										getShell(),
										UIPreferences.PAGE_COMMIT_PREFERENCES,
										new String[] { UIPreferences.PAGE_COMMIT_PREFERENCES },
										null);
						if (Window.OK == dialog.open())
							commitText.reconfigure();
					}
