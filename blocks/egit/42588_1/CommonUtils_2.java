	public static <T> T getAdapter(Object element, Class<T> adapterType) {
		if (adapterType.isInstance(element)) {
			return adapterType.cast(element);
		}
		if (element instanceof IAdaptable) {
			Object adapted = ((IAdaptable) element).getAdapter(adapterType);
			if (adapterType.isInstance(adapted)) {
				return adapterType.cast(adapted);
			}
		}
		Object adapted = Platform.getAdapterManager().getAdapter(element,
				adapterType);
		if (adapterType.isInstance(adapted)) {
			return adapterType.cast(adapted);
		}
		return null;
	}

