	private void setBackgroundImageForChildren(Composite parent, Image image) {
		for (Control child : parent.getChildren()) {
			if (child.getBackgroundImage() == null) {
				child.setBackground(image.getBackground());
				child.setBackgroundImage(null);
			}
		}
	}

