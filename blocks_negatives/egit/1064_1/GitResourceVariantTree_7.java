	/**
	 * Retrieves the name of the branch that this variant tree should be
	 * compared against for the given resource.
	 *
	 * @param resource
	 *            the resource that is being compared for
	 * @return the name of the target comparison branch
	 * @throws IOException
	 */
	abstract Tree getRevTree(IResource resource) throws IOException;

	abstract ObjectId getRevObjId(IResource resource) throws IOException;

	/**
	 * Initializes the repository information for the specified resource.
	 *
	 * @param resource
	 *            the resource that needs to have its repository information
	 *            initialized for
	 * @throws IOException
	 *             if an error occurs while walking the branch
	 */
	private synchronized void initialize(IResource resource) throws IOException {
		IProject project = resource.getProject();
		if (!gsdData.contains(project)) {
			return;
		}
