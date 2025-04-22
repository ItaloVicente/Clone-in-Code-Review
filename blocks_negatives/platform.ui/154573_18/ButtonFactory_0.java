
	/**
	 * Sets the application defined widget data associated with the receiver to be
	 * the argument. The <em>widget data</em> is a single, unnamed field that is
	 * stored with every widget.
	 *
	 * @param data the widget data
	 * @return this
	 *
	 * @see Button#setData(Object)
	 */
	public ButtonFactory data(Object data) {
		addProperty(b -> b.setData(data));
		return this;
	}
