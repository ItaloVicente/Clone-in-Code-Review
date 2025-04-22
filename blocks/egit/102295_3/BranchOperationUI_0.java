							MessageDialog.INFORMATION,
							new String[] { IDialogConstants.CLOSE_LABEL },
							0, toggleMessage, false);
					int result = dialog.open();
					if (result == IDialogConstants.CLOSE_ID
							&& dialog.getToggleState()) {
						store.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
								false);
					}
