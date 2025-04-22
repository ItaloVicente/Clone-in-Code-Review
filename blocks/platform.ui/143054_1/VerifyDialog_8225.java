		text.append(label).append(" failed on the ").append(Util.getWS())
				.append(" platform:\n");

		String failureMessages[] = test.failureTexts();
		for (int i = 0; i < test.checkListTexts().size(); i++) {
			if (!_checkList[i].getSelection()) {
				text.append("- ").append(failureMessages[i]).append("\n");
			}
		}
		FailureDialog dialog = new FailureDialog(getShell());
		dialog.create();
		dialog.setText(text.toString());
		if (dialog.open() == IDialogConstants.OK_ID) {
			_failureText = dialog.toString();
			setReturnCode(IDialogConstants.NO_ID);
			if (_testDialog.getShell() != null) {
				_testDialog.close();
			}
			close();
		}
	}

	@Override
