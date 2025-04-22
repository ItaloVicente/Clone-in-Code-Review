		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog
					.openError(getContainer().getShell(), DataTransferMessages.FileImport_importProblems,
							null, // no special message
							status);
			return false;
		}

		return true;
	}
