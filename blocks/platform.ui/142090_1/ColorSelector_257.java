				e.result = JFaceResources.getString("ColorSelector.Name"); //$NON-NLS-1$
			}
		});
	}

	public void addListener(IPropertyChangeListener listener) {
		addListenerObject(listener);
	}

	private Point computeImageSize(Control window) {
		GC gc = new GC(window);
		Font f = JFaceResources.getFontRegistry().get(
				JFaceResources.DIALOG_FONT);
		gc.setFont(f);
		int height = gc.getFontMetrics().getHeight();
		gc.dispose();
		Point p = new Point(height * 3 - 6, height);
		return p;
	}

	public Button getButton() {
		return fButton;
	}

	public RGB getColorValue() {
		return fColorValue;
	}

	public void removeListener(IPropertyChangeListener listener) {
		removeListenerObject(listener);
	}

	public void setColorValue(RGB rgb) {
		fColorValue = rgb;
		updateColorImage();
	}

	public void setEnabled(boolean state) {
		getButton().setEnabled(state);
	}

	protected void updateColorImage() {
		Display display = fButton.getDisplay();
		GC gc = new GC(fImage);
		gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		gc.drawRectangle(0, 2, fExtent.x - 1, fExtent.y - 4);
		if (fColor != null) {
