	/**
	 * @param element
	 * @param adapterType
	 * @param load
	 * @return the adapted element, or null
	 */
	private static Object getAdapter(Object element, Class adapterType,
			boolean load) {
		if (adapterType.isInstance(element))
			return element;
		if (element instanceof IAdaptable) {
			Object adapted = CommonUtils.getAdapter(((IAdaptable) element), adapterType);
			if (adapterType.isInstance(adapted))
				return adapted;
		}
		if (load) {
			Object adapted = Platform.getAdapterManager().loadAdapter(element,
					adapterType.getName());
			if (adapterType.isInstance(adapted))
				return adapted;
		} else {
			Object adapted = Platform.getAdapterManager().getAdapter(element,
					adapterType);
			if (adapterType.isInstance(adapted))
				return adapted;
		}
		return null;
	}

