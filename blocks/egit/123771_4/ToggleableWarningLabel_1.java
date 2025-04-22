	protected void setImage(Image image) {
		this.image.setImage(image);
		isBuiltInImage = false;
	}

	protected void setText(String message) {
		warningText.setText(message);
		layout(true);
		changeVisibility(true);
	}

	protected void changeVisibility(boolean visible) {
