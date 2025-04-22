
	/**
	 * The model providers need information about the remote sides to properly
	 * detect whether a given file is part of a logical model or not. This will
	 * prepare the RemoteResourceMappingContext corresponding to the given
	 * source branch ("ours" side of the comparison, {@code leftRev} or the work
	 * tree, depending on the state of {@code inclueLocal}) and the given
	 * destination branch ("theirs" side, {@code rightRev}). The common ancestor
	 * ("base" side) for this comparison will be inferred as the first common
	 * ancestor of {@code leftRev} and {@code rightRev}.
	 *
	 * @param repository
	 *            The repository from which we're currently comparing or
	 *            synchronizing files.
	 * @param leftRev
	 *            Left revision of the comparison (usually the local or "new"
	 *            revision). Won't be used if <code>includeLocal</code> is
	 *            <code>true</code>.
	 * @param rightRev
	 *            Right revision of the comparison (usually the "old" revision).
	 * @param includeLocal
	 *            <code>true</code> if we are to consider local data (work tree)
	 *            as being the source of this comparison. <code>false</code> if
	 *            we are to use the data from <code>leftRev</code> for that.
	 * @return a {@link RemoteResourceMappingContext} ready for use by the model
	 *         providers.
	 * @throws IOException
	 */
	public static RemoteResourceMappingContext prepareContext(
			Repository repository, String leftRev, String rightRev,
			boolean includeLocal) throws IOException {
		GitSynchronizeData gsd = new GitSynchronizeData(repository, leftRev,
				rightRev, includeLocal);
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		return new GitSubscriberResourceMappingContext(subscriber, gsds);
	}
