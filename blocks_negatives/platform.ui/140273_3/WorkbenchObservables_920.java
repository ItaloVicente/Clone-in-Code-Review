	 * @param master
	 *            the observable whose value should be adapted
	 * @param adapter
	 *            the target type
	 * @param adapterManager
	 *            the adapter manager used to adapt the master value
	 * @return an observable with values of the given type, or <code>null</code>
	 *         if the current value of the given observable does not adapt to
	 *         the target type
