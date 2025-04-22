	/**
	 * {@inheritDoc}
	 * <p>
	 * Objects known to exist but not expressed by {@code #getAllRefs()}.
	 * <p>
	 * When a repository borrows objects from another repository, it can
	 * advertise that it safely has that other repository's references, without
	 * exposing any other details about the other repository. This may help a
	 * client trying to push changes avoid pushing more than it needs to.
	 */
	@Override
	public Set<ObjectId> getAdditionalHaves() {
		return getAdditionalHaves(null);
	}

