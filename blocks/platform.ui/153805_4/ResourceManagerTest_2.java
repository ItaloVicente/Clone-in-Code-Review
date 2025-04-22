
	private static class MyImageDataProvider implements ImageDataProvider {
		private final String id;

		public MyImageDataProvider(String id) {
			this.id = id;
		}

		@Override
		public ImageData getImageData(int zoom) {
			PaletteData paletteData = new PaletteData(new RGB(255, 0, 0), new RGB(0, 255, 0));
			ImageData imageData = new ImageData(48, 48, 1, paletteData);
			for (int x = 11; x < 35; x++) {
				for (int y = 11; y < 35; y++) {
					imageData.setPixel(x, y, 1);
				}
			}

			return imageData;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			MyImageDataProvider other = (MyImageDataProvider) obj;
			return Objects.equals(id, other.id);
		}

	}
