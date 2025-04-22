	static <T> T internalGetAdapter(Object o, Class<T> adapterType) {
		if (o instanceof IAdaptable) {
			IAdaptable element = (IAdaptable) o;
			T adapted = element.getAdapter(adapterType);
			if (adapted != null) {
				return adapterType.cast(adapted);
			}
		}
		return Platform.getAdapterManager().getAdapter(o, adapterType);
	}

