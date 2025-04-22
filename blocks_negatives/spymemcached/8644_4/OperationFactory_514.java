	/**
	 * Create a mutator operation.
	 *
	 * @param m the mutator type
	 * @param key the mutatee key
	 * @param by the amount to increment or decrement
	 * @param def the default value
	 * @param exp expiration in case we need to default (0 if no default)
	 * @param cb the status callback
	 * @return the new mutator operation
	 */
	MutatorOperation mutate(Mutator m, String key, int by,
			long def, int exp, OperationCallback cb);
