	private void setBackgroundImageForChildren(Composite parent, Image image) {
		for (Control child : parent.getChildren()) {
			if (child.getBackgroundImage() != image) {
				child.setBackgroundImage(image);
			}
		}
	}

