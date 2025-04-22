	/**
	 * @param element
	 * @param adapterType
	 * @return the adapted element, or null
	 */
	public static <T> T getAdapterForObject(Object element, Class<T> adapterType) {
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

	/**
	 * Returns the adapter corresponding to the given adapter class.
	 * <p>
	 * Workaround for "Unnecessary cast" errors, see bug 460685. Can be removed
	 * when EGit depends on Eclipse 4.5 or higher.
	 *
	 * @param adaptable
	 *            the adaptable
	 * @param adapterClass
	 *            the adapter class to look up
	 * @return a object of the given class, or <code>null</code> if this object
	 *         does not have an adapter for the given class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAdapter(IAdaptable adaptable,
			Class<T> adapterClass) {
		Object adapter = adaptable.getAdapter(adapterClass);
		return (T) adapter;
	}


