	 * @param parent
	 *            the parent locator
	 * @param factory
	 *            a factory that can lazily provide services if requested. This
	 *            may be <code>null</code>
	 * @param owner
	 *            an object whose {@link IDisposable#dispose()} method will be
	 *            called on the UI thread if the created service locator needs
	 *            to be disposed (typically, because a plug-in contributing
	 *            services to the service locator via an
	 *            {@link AbstractServiceFactory} is no longer available). The
	 *            owner can be any object that implements {@link IDisposable}.
	 *            The recommended implementation of the owner's dispose method
	 *            is to do whatever is necessary to stop using the created
	 *            service locator, and then to call
	 *            {@link IDisposable#dispose()} on the service locator.
	 * @return the created service locator. The returned service locator will be
	 *         an instance of {@link IDisposable}.
