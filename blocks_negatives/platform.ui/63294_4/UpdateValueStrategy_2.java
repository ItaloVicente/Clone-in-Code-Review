	/**
	 * Converts the value from the source type to the destination type.
	 * <p>
	 * Default implementation will use the {@link #setConverter(IConverter)
	 * converter} if one exists. If no converter exists no conversion occurs.
	 * </p>
	 *
	 * @param value
	 * @return the converted value
	 */
	public Object convert(Object value) {
		return converter == null ? value : converter.convert(value);
	}

