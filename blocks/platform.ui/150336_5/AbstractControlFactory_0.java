
	public F foreground(Color color) {
		addProperty(c -> c.setForeground(color));
		return cast(this);
	}

	public F background(Color color) {
		addProperty(c -> c.setBackground(color));
		return cast(this);
	}

	public F orientation(int orientation) {
		addProperty(t -> t.setOrientation(orientation));
		return cast(this);
	}
