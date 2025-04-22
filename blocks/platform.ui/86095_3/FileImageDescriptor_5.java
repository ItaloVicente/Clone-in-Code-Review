	private class ImageProvider implements ImageFileNameProvider {
		@Override
		public String getImagePath(int zoom) {
			String xName = getxName(name, zoom);
			if (xName != null) {
				return getFilePath(xName, zoom == 100);
			}
			return null;
		}
	}

