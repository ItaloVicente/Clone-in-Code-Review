    @Override
	public ImageData getImageData() {
        Image image = internalCreateImage(Display.getCurrent());
        ImageData result = image.getImageData();
        image.dispose();
        return result;
    }
