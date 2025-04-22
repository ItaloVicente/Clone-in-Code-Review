							MessageDialog.INFORMATION,
							new String[] { IDialogConstants.CLOSE_LABEL },
							0, toggleMessage, false);
					dialog.open();
					if (dialog.getToggleState()) {
						store.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
								false);
					}
