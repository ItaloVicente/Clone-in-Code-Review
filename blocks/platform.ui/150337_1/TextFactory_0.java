		addProperty(t -> t.addVerifyListener(listener));
		return this;
	}

	public TextFactory orientate(int orientation) {
		addProperty(t -> t.setOrientation(orientation));
