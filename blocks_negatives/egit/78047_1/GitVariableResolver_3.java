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
	private static <T> T getAdapterFromManager(Object adaptable,
			Class<T> adapterClass) {
		Object adapter = Platform.getAdapterManager().getAdapter(adaptable,
				IResource.class);
		return (T) adapter;
	}

