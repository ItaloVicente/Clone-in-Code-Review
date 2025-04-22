					String[] buttons;
					if (canCancel) {
						buttons = new String[] { WorkbenchMessages.Save, WorkbenchMessages.Dont_Save,
								IDialogConstants.CANCEL_LABEL };
					} else {
						buttons = new String[] { WorkbenchMessages.Save, WorkbenchMessages.Dont_Save };
					}

