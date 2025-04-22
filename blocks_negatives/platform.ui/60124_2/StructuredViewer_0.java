	/**
	 * Sets the content provider used by this viewer.
	 * <p>
	 * The <code>StructuredViewer</code> implementation of this method calls the
	 * super implementation after checking if the given content provider is a
	 * valid one for the viewer. The check is done by
	 * <code>assertContentProviderType</code>, which should be overridden in
	 * subclasses requiring support for additional content provider types.
	 * Overriding this method is generally not required; however, if overriding
	 * in a subclass, <code>super.setContentProvider</code> must be invoked.
	 * </p>
	 *
	 * @param provider
	 *            the content provider
	 * @see #getContentProvider
	 * @see #assertContentProviderType
	 */
