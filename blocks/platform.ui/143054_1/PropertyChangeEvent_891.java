	public PropertyChangeEvent(Object source, String property, Object oldValue,
			Object newValue) {
		super(source);
		Assert.isNotNull(property);
		this.propertyName = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
