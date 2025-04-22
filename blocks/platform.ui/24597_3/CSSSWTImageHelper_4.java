
	public static void setImage(Button button, Image image) {
		if (button.getImage() != image) {
			storeDefaultImage(button);
			button.setImage(image);
		}
	}

	public static void setBackgroundImage(Control control, Image image) {
		if (control instanceof Control && control.getBackgroundImage() != image) {
			control.setBackgroundImage(image);
		}
	}
