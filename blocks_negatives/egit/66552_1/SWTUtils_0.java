	/**
	 * Decorates the image with the given overlay
	 * @param image
	 * @param overlay
	 * @return the decorated image
	 */
	public static Image getDecoratedImage(final Image image, final ImageDescriptor overlay) {
		Image decoratedImage;
		CompositeImageDescriptor cd = new CompositeImageDescriptor() {

			@Override
			protected Point getSize() {
				Rectangle bounds = image.getBounds();
				return new Point(bounds.width, bounds.height);
			}

			@Override
			protected void drawCompositeImage(int width, int height) {
				drawImage(image.getImageData(), 0, 0);
				drawImage(overlay.getImageData(), 0, 0);

			}
		};
		decoratedImage = cd.createImage();

		return decoratedImage;
	}

