
	private boolean isImagesRefreshRequired(ImageBasedFrame frame) {
		Object handleImage = frame.getData("handleImage");
		if (handleImage instanceof Image && ((Image) handleImage).isDisposed()) {
			return true;
		}

		Object frameImage = frame.getData("frameImage");
		if (frameImage instanceof Image && ((Image) frameImage).isDisposed()) {
			return true;
		}

		return false;
	}
