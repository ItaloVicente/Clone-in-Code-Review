	/**
	 * CAS a new value in for a key.
	 *
	 * <p>
	 * Note that if initial is null, this method will only update existing
	 * values.
	 * </p>
	 *
	 * @param key the key to be CASed
	 * @param initial the value to use when the object is not cached
	 * @param initialExp the expiration time to use when initializing
	 * @param m the mutation to perform on an object if a value exists for the
	 *          key
	 * @return the new value that was set
	 */
	public T cas(final String key, final T initial, int initialExp,
			final CASMutation<T> m) throws Exception {
		T rv=initial;
