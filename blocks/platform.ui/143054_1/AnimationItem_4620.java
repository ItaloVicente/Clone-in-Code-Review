		animationManager.addItem(this);
	}

	protected abstract Control createAnimationItem(Composite parent);

	void paintImage(PaintEvent event, Image image, ImageData imageData) {
		event.gc.drawImage(image, 0, 0);
	}

	public abstract Control getControl();

	void animationStart() {
		animationContainer.animationStart();
	}

	void animationDone() {
		animationContainer.animationDone();
	}

	public int getPreferredWidth() {
		return animationManager.getPreferredWidth() + 5;
	}

	void setAnimationContainer(IAnimationContainer container) {
		this.animationContainer = container;
	}
