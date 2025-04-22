				}
			}
			return true;
		}
		TransferData[] transfers = clipboard.getAvailableTypes();
		FileTransfer fileTransfer = FileTransfer.getInstance();
		for (TransferData transfer : transfers) {
			if (fileTransfer.isSupportedType(transfer)) {
