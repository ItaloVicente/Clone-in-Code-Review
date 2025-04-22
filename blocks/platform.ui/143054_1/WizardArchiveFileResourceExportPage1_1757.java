			} else {
				displayErrorDialog(DataTransferMessages.ZipExport_alreadyExistsError);
				giveFocusToDestination();
				return false;
			}
		}

		return true;
	}

	protected boolean ensureTargetIsValid() {
		String targetPath = getDestinationValue();

		if (!ensureTargetDirectoryIsValid(targetPath)) {
			return false;
		}

		if (!ensureTargetFileIsValid(new File(targetPath))) {
