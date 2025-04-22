	private TransferData getSupportedTransferType(TransferData[] dataTypes,
			TransferDropTargetListener listener) {
		for (TransferData dataType : dataTypes) {
			if (listener.getTransfer().isSupportedType(dataType)) {
				return dataType;
			}
		}
		return null;
	}
