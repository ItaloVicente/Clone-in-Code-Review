/**
	 * @param element
	 * @param adapterType
	 * @return the adapted element, or null
	 */
	public static Object getAdapter(Object element, Class adapterType) {
		return getAdapter(element, adapterType, false);
	}
