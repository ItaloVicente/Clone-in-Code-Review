
	/**
	 * Sets the data for the button.
	 *
	 * @param data
	 * @return this
	 */
	public ButtonFactory data(Object data) {
		addProperty(b -> b.setData(data));
		return this;
	}
