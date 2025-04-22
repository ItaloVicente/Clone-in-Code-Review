
		retVal = dialog.open();
		editorDesc = dialog.getSelectedEditor();

		if (retVal == IDialogConstants.OK_ID && editorDesc != null) {
			status = IStatus.OK;
		} else if (IDialogConstants.CANCEL_ID == retVal) {
			status = IStatus.CANCEL;
		} else if (editorDesc == null)
			status = IStatus.ERROR;

		return editorDesc;
