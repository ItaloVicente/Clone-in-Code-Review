	private void applyProperties(C control) {
		properties.forEach(p -> p.apply(control));
	}

	protected void addProperty(Property<C> a) {
		this.properties.add(a);
