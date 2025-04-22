		lastDataType = event.dataType;
		if (LocalSelectionTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = LocalSelectionTransfer.getInstance().getSelection();
			return;
		}
		if (ResourceTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = resources;
			return;
		}
		if (!FileTransfer.getInstance().isSupportedType(event.dataType)) {
