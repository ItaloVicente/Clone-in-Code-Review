		setErrorMessage(DataTransferMessages.FileImport_invalidSource);
		return false;
	}

	protected boolean executeImportOperation(ImportOperation op) {
		initializeOperation(op);

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			displayErrorDialog(e.getTargetException());
