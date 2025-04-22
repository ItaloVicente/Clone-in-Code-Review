			};
			dialog.open();
			if (dialog.getReturnCode() == SWT.DEFAULT) {
				result[0] = IDialogConstants.CANCEL_ID;
			} else {
				result[0] = resultId[dialog.getReturnCode()];
