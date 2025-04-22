		Iterator<TransferDropTargetListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			TransferDropTargetListener listener = iter
					.next();
			TransferData dataType = getSupportedTransferType(event.dataTypes,
					listener);
			if (dataType != null) {
				TransferData originalDataType = event.currentDataType;
				event.currentDataType = dataType;
				if (listener.isEnabled(event)) {
					if (!setCurrentListener(listener, event)) {
