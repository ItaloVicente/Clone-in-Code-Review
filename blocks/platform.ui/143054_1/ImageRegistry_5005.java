	private static class Entry {
		protected Image image;

		protected ImageDescriptor descriptor;
	}

	private static class OriginalImageDescriptor extends ImageDescriptor {
		private Image original;
		private int refCount = 0;
		private Device originalDisplay;

		public OriginalImageDescriptor(Image original, Device originalDisplay) {
			this.original = original;
			this.originalDisplay = originalDisplay;
		}

		@Override
