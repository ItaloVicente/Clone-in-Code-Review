							MessageDialog.INFORMATION,
							new String[] { IDialogConstants.CLOSE_LABEL },
							0, toggleMessage, false);
					md.setPrefStore(store);
					md.setPrefKey(UIPreferences.SHOW_DETACHED_HEAD_WARNING);
					md.open();
