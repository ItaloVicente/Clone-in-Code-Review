	public Image adornImage(Image toAdorn, Image adornment) {
		if (toAdorn == null)
			return null;
		if (adornment == null)
			return toAdorn;
		Rectangle adornmentSize = adornment.getBounds();

		Image adornedImage = new Image(toAdorn.getDevice(), 16, 16);
		GC gc = new GC(adornedImage);
		gc.drawImage(toAdorn, 0, 0);
		gc.drawImage(adornment, 16 - adornmentSize.width, 0);
		gc.dispose();

		return adornedImage;
