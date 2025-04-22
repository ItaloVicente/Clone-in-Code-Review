
        if (fileSystemObjects.size() > 0) {
			return importResources(fileSystemObjects);
		}

        MessageDialog.openInformation(getContainer().getShell(),
                DataTransferMessages.DataTransfer_information,
                DataTransferMessages.FileImport_noneSelected);

