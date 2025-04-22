
	public TableColumnFactory moveable(boolean moveable) {
		addProperty(c -> c.setMoveable(moveable));
		return this;
	}

	public TableColumnFactory resizable(boolean resizable) {
		addProperty(c -> c.setResizable(resizable));
		return this;
	}
