	public Image createImage(boolean returnMissingImageOnError, Device device) {
		try {
			return new Image(device, this::getImageData);
		} catch (IllegalArgumentException | SWTException e) {
			if (returnMissingImageOnError) {
				try {
					return new Image(device, DEFAULT_IMAGE_DATA);
				} catch (SWTException e2) {
					return null;
				}
			}
			return null;
		}
	}
