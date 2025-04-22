
	public TreeColumnFactory moveable(boolean moveable) {
		addProperty(c -> c.setMoveable(moveable));
		return this;
	}

	public TreeColumnFactory resizable(boolean resizable) {
		addProperty(c -> c.setResizable(resizable));
		return this;
	}
