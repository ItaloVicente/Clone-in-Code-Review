		if (InternalPolicy.DEBUG_LOAD_URL_IMAGE_DESCRIPTOR_2x) {
			try {
				return new Image(device, new ImageProvider());
			} catch (SWTException exception) {
			} catch (IllegalArgumentException exception) {
			}
		}

		String path = getFilePath(name, true);
