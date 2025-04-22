	/**
	 * Retrieves the service corresponding to the given API.
	 * <p>
	 * Workaround for "Unnecessary cast" errors, see bug 441615. Can be removed
	 * when EGit depends on Eclipse 4.5 or higher.
	 *
	 * @param locator
	 *            the service locator, must not be null
	 * @param api
	 *            the interface the service implements, must not be null
	 * @return the service, or null if no such service could be found
	 * @see IServiceLocator#getService(Class)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(IServiceLocator locator, Class<T> api) {
		Object service = locator.getService(api);
		return (T) service;
	}

