
	private static class PreviewDecoration implements IDecoration {

		private List<String> prefixes = new ArrayList<String>();

		private List<String> suffixes = new ArrayList<String>();

		private ImageDescriptor overlay = null;

		private Color backgroundColor = null;

		private Font font = null;

		private Color foregroundColor = null;

		/**
		 * Adds an icon overlay to the decoration
		 * <p>
		 * Copies the behavior of <code>DecorationBuilder</code> of only
		 * allowing the overlay to be set once.
		 */
		public void addOverlay(ImageDescriptor overlayImage) {
			if (overlay == null)
				overlay = overlayImage;
		}

		public void addOverlay(ImageDescriptor overlayImage, int quadrant) {
			addOverlay(overlayImage);
		}

		public void addPrefix(String prefix) {
			prefixes.add(prefix);
		}

		public void addSuffix(String suffix) {
			suffixes.add(suffix);
		}

		public IDecorationContext getDecorationContext() {
			return new DecorationContext();
		}

		public void setBackgroundColor(Color color) {
			backgroundColor = color;
		}

		public void setForegroundColor(Color color) {
			foregroundColor = color;
		}

		public void setFont(Font font) {
			this.font = font;
		}

		public ImageDescriptor getOverlay() {
			return overlay;
		}

		public Color getBackgroundColor() {
			return backgroundColor;
		}

		public Color getForegroundColor() {
			return foregroundColor;
		}

		public Font getFont() {
			return font;
		}

		public String getPrefix() {
			StringBuilder sb = new StringBuilder();
			for (Iterator<String> iter = prefixes.iterator(); iter.hasNext();) {
				sb.append(iter.next());
			}
			return sb.toString();
		}

		public String getSuffix() {
			StringBuilder sb = new StringBuilder();
			for (Iterator<String> iter = suffixes.iterator(); iter.hasNext();) {
				sb.append(iter.next());
			}
			return sb.toString();
		}

	}
