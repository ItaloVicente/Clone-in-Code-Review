	/**
	 * Returns all refs.
	 * <p>
	 * Callers interested in only a portion of the ref hierarchy can call
	 * {@link #getRefsByPrefix} instead.
	 *
	 * @return immutable list of all refs.
	 * @throws java.io.IOException
	 *             the reference space cannot be accessed.
	 * @since 5.0
	 */
	@NonNull
	public List<Ref> getAllRefs() throws IOException {
		return getRefsByPrefix(ALL);
	}

