	private void setBackgroundImageForChildren(Control parent, Image image) {
		RGB parentRGB = parent.getBackground() != null ? parent.getBackground()
				.getRGB() : null;
		for (Control child : ((Composite) parent).getChildren()) {
			if (child.getBackground() != null
					&& child.getBackground().getRGB().equals(parentRGB)) {
				child.setBackground(null);
			}
		}
	}

