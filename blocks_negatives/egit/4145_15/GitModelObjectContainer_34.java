	public SaveableComparison getSaveable() {
		return null;
	}

	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
	}

	public String getFullPath() {
		return getLocation().toPortableString();
	}

	public boolean isCompareInputFor(Object object) {
		return false;
	}

	/**
	 * This method is used for lazy loading list of containrer's children
	 *
	 * @return list of children in this container
	 */
	protected abstract GitModelObject[] getChildrenImpl();

	/**
	 *
	 * @param tw instance of {@link TreeWalk} that should be used
	 * @param ancestorCommit TODO
	 * @param ancestorNth
	 * @param remoteNth
	 * @param baseNth
	 * @return {@link GitModelObject} instance of given parameters
	 * @throws IOException
	 */
	protected GitModelObject getModelObject(TreeWalk tw, RevCommit ancestorCommit,
			int ancestorNth, int remoteNth, int baseNth) throws IOException {

		ObjectId objRemoteId;
		if (remoteNth > -1)
			objRemoteId = tw.getObjectId(remoteNth);
		else
			objRemoteId = ObjectId.zeroId();

		ObjectId objBaseId;
		if (baseNth > -1)
			objBaseId = tw.getObjectId(baseNth);
		else
			objBaseId = ObjectId.zeroId();

		ObjectId objAncestorId;
		if (ancestorNth > -1)
			objAncestorId = tw.getObjectId(ancestorNth);
		else
			objAncestorId = ObjectId.zeroId();

		int objectType = Constants.OBJ_BAD;

		if (baseNth > -1)
			objectType = tw.getFileMode(baseNth).getObjectType();
		if (objectType == Constants.OBJ_BAD)
			objectType = tw.getFileMode(remoteNth).getObjectType();
		if (objectType == Constants.OBJ_BAD && ancestorNth > -1)
			objectType = tw.getFileMode(ancestorNth).getObjectType();

		if (objectType == Constants.OBJ_BLOB)
			return new GitModelBlob(this, getBaseCommit(), ancestorCommit,
					objAncestorId, objBaseId, objRemoteId, path);
		else if (objectType == Constants.OBJ_TREE)
			return new GitModelTree(this, getBaseCommit(), ancestorCommit,
					objAncestorId, objBaseId, objRemoteId, path);

		return null;
	}

	/**
	 * @return id of parent commit
	 */
	protected ObjectId getParentRevCommit() {
		return baseCommit.getParent(0);
	}

	private void calculateKind() {
		ObjectId remote = remoteCommit != null ? remoteCommit.getId() : zeroId();
		if (remote.equals(zeroId()))
			kind = kind | ADDITION;
		else if (baseCommit.equals(zeroId()))
			kind = kind | DELETION;
		else
			kind = kind | CHANGE;
	}

