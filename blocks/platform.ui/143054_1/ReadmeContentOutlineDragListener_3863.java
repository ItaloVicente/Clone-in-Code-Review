		if (PluginTransfer.getInstance().isSupportedType(event.dataType)) {
			byte[] segmentData = getSegmentText().getBytes();
			event.data = new PluginTransferData(ReadmeDropActionDelegate.ID, segmentData);
			return;
		}
		if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = getSegmentText();
			return;
		}
	}
