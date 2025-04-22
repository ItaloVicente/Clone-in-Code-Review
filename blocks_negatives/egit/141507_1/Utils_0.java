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
	public static <T> T getAdapter(IAdaptable adaptable, Class<T> adapterClass) {
		Object adapter = adaptable.getAdapter(adapterClass);
		if (adapter == null) {
			return null;
		}
		if (adapterClass.isInstance(adapter)) {
			return adapterClass.cast(adapter);
		} else {
			Activator.logError(
					MessageFormat.format(CoreText.Utils_InvalidAdapterError,
							adaptable.getClass().getName(),
							adapterClass.getName(),
							adapter.getClass().getName()),
					new IllegalStateException());
			return null;
		}
	}

