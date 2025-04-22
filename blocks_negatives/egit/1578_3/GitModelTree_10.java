		children = result.toArray(new GitModelObject[result.size()]);
	}

	private GitModelObject createChildren(TreeWalk tw, int ancestorNth,
			int baseNth, int remoteNth) throws IOException {
		ObjectId objRemoteId = tw.getObjectId(remoteNth);
		if (objRemoteId.equals(zeroId()))
			return null;

		String objName = tw.getNameString();
		ObjectId objBaseId = baseNth != -1 ? tw.getObjectId(baseNth) : zeroId();
		ObjectId objAncestorId = ancestorNth != -1 ? tw
				.getObjectId(ancestorNth) : zeroId();
		int objectType = tw.getFileMode(remoteNth).getObjectType();

		if (objectType == Constants.OBJ_BLOB)
			return new GitModelBlob(this, getRemoteCommit(), objAncestorId,
					objBaseId, objRemoteId, objName);
		else if (objectType == Constants.OBJ_TREE)
			return new GitModelTree(this, getRemoteCommit(), objAncestorId,
					objBaseId, objRemoteId, objName);

		return null;
