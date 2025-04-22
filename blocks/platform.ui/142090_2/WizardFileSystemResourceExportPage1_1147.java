			if (!directory.mkdirs()) {
				displayErrorDialog(DataTransferMessages.DataTransfer_directoryCreationError);
				giveFocusToDestination();
				return false;
			}
		}
