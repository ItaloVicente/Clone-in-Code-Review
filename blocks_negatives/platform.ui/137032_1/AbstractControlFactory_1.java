
	/**
	 * Casts to the concrete instance of factory class. Needs to be called by
	 * abstract factory classes.
	 *
	 * @param factory extending ControlFactory, usually "this"
	 * @return casted factory
	 */
	protected final F cast(ControlFactory<F, C> factory) {
		return factoryClass.cast(factory);
	}

	/**
	 * @param parent
	 * @return this
	 */
	public final C create(Composite parent) {
		C control = controlCreator.apply(parent);
		properties.forEach(p -> p.apply(control));
		return control;
	}

	/**
	 * Adds a property like image, text, enabled, listeners, ... to the control.
	 *
	 * <br/>
	 * Example:
	 *
	 * <pre>
	 * public LabelFactory text(String text) {
	 * 	addProperty(l -> l.setText(text));
	 * 	return this;
	 * }
	 * </pre>
	 *
	 * @param property usually a lambda
	 */
	protected final void addProperty(Property<C> property) {
		this.properties.add(property);
	}
