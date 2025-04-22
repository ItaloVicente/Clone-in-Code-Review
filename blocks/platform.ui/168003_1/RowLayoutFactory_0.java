	public Composite newCompositedFor(Composite c, int style) {
		Composite child = new Composite(c, style);
		child.setLayout(copyLayout(layout));
		return child;
	}

