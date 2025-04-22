	private Image getDecoratedImage(final Image image) {
		Image decoratedImage = decoratedImages.get(image);
		if (decoratedImage == null) {
			CompositeImageDescriptor cd = new CompositeImageDescriptor() {

				@Override
				protected Point getSize() {
					Rectangle bounds = image.getBounds();
					return new Point(bounds.width, bounds.height);
				}

				@Override
				protected void drawCompositeImage(int width, int height) {
					drawImage(image.getImageData(), 0, 0);
					drawImage(UIIcons.OVR_CHECKEDOUT.getImageData(), 0,
							0);

				}
			};
			decoratedImage = cd.createImage();
			decoratedImages.put(image, decoratedImage);
		}
		return decoratedImage;
	}

