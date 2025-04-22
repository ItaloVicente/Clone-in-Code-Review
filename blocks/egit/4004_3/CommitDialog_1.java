				String[] pages = new String[] { UIPreferences.PAGE_COMMIT_PREFERENCES };
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(), pages[0], pages,
								null);
				if (Window.OK == dialog.open())
					commitText.reconfigure();
			}
