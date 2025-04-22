		final DecorationHelper helper = new DecorationHelper();
		helper.decorate(decoration, config);
	}


	/**
	 * Helper class for doing repository decoration
	 *
	 * Used for real-time decoration, as well as in the decorator preview
	 * preferences page
	 */
	private static class DecorationHelper {

		/**
		 * Define a cached image descriptor which only creates the image data
		 * once
		 */
		private static class CachedImageDescriptor extends ImageDescriptor {
			private ImageDescriptor descriptor;

			private ImageData data;

			public CachedImageDescriptor(ImageDescriptor descriptor) {
				this.descriptor = descriptor;
			}
