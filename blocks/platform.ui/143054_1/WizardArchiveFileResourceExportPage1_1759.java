		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog.openError(getContainer().getShell(),
					DataTransferMessages.DataTransfer_exportProblems,
					null, // no special message
					status);
			return false;
		}

		return true;
	}

	@Override
