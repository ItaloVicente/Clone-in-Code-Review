		addProperty(t -> t.addVerifyListener(listener));
		return this;
	}

	public TextFactory orientation(int orientation) {
		addProperty(t -> t.setOrientation(orientation));
