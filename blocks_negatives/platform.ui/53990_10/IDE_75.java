					@Override
					protected void buttonPressed(int id) {
						if (id == IDialogConstants.YES_ID) {
							super.buttonPressed(IDialogConstants.OK_ID);
						} else if (id == IDialogConstants.NO_ID) {
							super.buttonPressed(IDialogConstants.CANCEL_ID);
						}
						super.buttonPressed(id);
