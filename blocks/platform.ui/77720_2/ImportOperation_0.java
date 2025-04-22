		if (noReadPermissions(fileObject)) {
			errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					NLS.bind(DataTransferMessages.ImportOperation_cannotReadError, fileObjectPath), null));
			return;
		}

