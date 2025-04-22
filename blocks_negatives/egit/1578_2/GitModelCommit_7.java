		children = result.toArray(new GitModelObject[result.size()]);
	}

	private GitModelObject getModelObject(TreeWalk tw, int ancestorNth,
			int baseNth, int actualNth) throws IOException {
		String objName = tw.getNameString();

		ObjectId objBaseId;
		if (baseNth > -1)
			objBaseId = tw.getObjectId(baseNth);
		else
			objBaseId = ObjectId.zeroId();

		ObjectId objRemoteId = tw.getObjectId(actualNth);
		ObjectId objAncestorId = tw.getObjectId(ancestorNth);
		int objectType = tw.getFileMode(actualNth).getObjectType();

		if (objectType == Constants.OBJ_BLOB)
			return new GitModelBlob(this, remoteCommit, objAncestorId,
					objBaseId, objRemoteId, objName);
		else if (objectType == Constants.OBJ_TREE)
			return new GitModelTree(this, remoteCommit, objAncestorId,
					objBaseId, objRemoteId, objName);

		return null;
	}

	private void calculateKind(ObjectId baseId, ObjectId remoteId) {
		if (baseId.equals(zeroId()))
			kind = kind | ADDITION;
		else if (remoteId.equals(zeroId()))
			kind = kind | DELETION;
		else
			kind = kind | CHANGE;
	}

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
