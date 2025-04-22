
		openWarning(operationResult);

		if (STOPPED.equals(status)) {
			try {
				showInteractiveRebaseView();
			} catch (PartInitException e) {
				return error(e.getMessage(), e);
			}
