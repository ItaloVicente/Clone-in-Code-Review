	/**
	 * When an element is added to the destination converts the element from the
	 * source element type to the destination element type.
	 * <p>
	 * Default implementation will use the {@link #setConverter(IConverter)
	 * converter} if one exists. If no converter exists no conversion occurs.
	 * </p>
	 *
	 * @param element
	 * @return the converted element
	 */
	public Object convert(Object element) {
		return converter == null ? element : converter.convert(element);
	}

