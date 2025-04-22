	/**
	 * Get the EOL stream type of the current entry using the config and
	 * {@link #getAttributes()}.
	 *
	 * @return the EOL stream type of the current entry using the config and
	 *         {@link #getAttributes()}. Note that this method may return null
	 *         if the {@link org.eclipse.jgit.treewalk.TreeWalk} is not based on
	 *         a working tree
	 * @since 4.3
	 * @deprecated use {@link #getEolStreamType(OperationType)} instead.
	 */
	@Deprecated
	public @Nullable EolStreamType getEolStreamType() {
		return (getEolStreamType(operationType));
	}

