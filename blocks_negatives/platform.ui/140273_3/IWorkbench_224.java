	 * Note that if the element implements <code>IAdaptable</code>,
	 * decorators may use this mechanism to obtain an adapter (for example an
	 * <code>IResource</code>), and derive the decoration from the adapter
	 * rather than the element. Since the adapter may differ from the original
	 * element, those using the decorator manager should be prepared to handle
	 * notification that the decoration for the adapter has changed, in addition
	 * to handling notification that the decoration for the element has changed.
	 * That is, it needs to be able to map back from the adapter to the element.
