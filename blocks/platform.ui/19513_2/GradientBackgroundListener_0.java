	private void setBackgroundImageForChildren(Composite parent, Image image) {
		for (Control child : parent.getChildren()) {
			child.setBackground(image.getBackground());
			child.setBackgroundImage(null);
		}
	}

