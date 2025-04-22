	@Override
	public Color getUsedMemColor() {
		return usedMemCol;
	}

	@Override
	public void setUsedMemColor(Color color) {
		sepCol = topLeftCol = armCol = usedMemCol = color;
	}

	@Override
	public Color getLowMemColor() {
		return lowMemCol;
	}

	@Override
	public void setLowMemColor(Color color) {
		lowMemCol = color;
	}

	@Override
	public Color getFreeMemColor() {
		return freeMemCol;
	}

	@Override
	public void setFreeMemColor(Color color) {
		freeMemCol = color;
	}

