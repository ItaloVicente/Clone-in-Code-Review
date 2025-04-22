	/**
	 * @param res
	 *            a resource
	 * @return the submodule repository if the resource is contained in a git
	 *         submodule otherwise return {@code null}. The returned repository
	 *         instance will always be taken from the {@link RepositoryCache}
	 *         and the caller should not call close() on it.
	 *
	 * @deprecated Since 4.3. Use {@link #getMapping(IResource)} and then
	 *             {@link #getRepository()} on the returned mapping instead; it
	 *             will return a submodule repository if the resource is in one.
	 */
	@Deprecated
	@Nullable
	public synchronized Repository getSubmoduleRepository(@NonNull IResource res) {
		RepositoryMapping mapping = getMapping(res);
		if (mapping == null) {
			return null;
		}
		return mapping.getRepository();
	}

