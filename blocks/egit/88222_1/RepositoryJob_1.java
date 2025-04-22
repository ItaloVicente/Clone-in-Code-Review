				boolean showDialog = dialogPreference == null
						|| Activator.getDefault().getPreferenceStore()
								.getBoolean(dialogPreference);
				if (showDialog) {
					if (isModal()) {
						showResult(action);
					} else {
						showResultDeferred(action);
					}
