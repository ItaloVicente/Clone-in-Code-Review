	private void applyProperties(C control) {
		properties.forEach(p -> p.apply(control));
	}

	protected final void addProperty(Property<C> property) {
		this.properties.add(property);
