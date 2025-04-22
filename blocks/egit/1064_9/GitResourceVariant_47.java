		if (name == null && path != null) {
			int lastSeparator = path.lastIndexOf('/');
			if (lastSeparator > -1)
				name = path.substring(lastSeparator + 1);
			else
				name = path;
		}

		return name;
	}

	@Override
	public int hashCode() {
		return objectId.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GitResourceVariant)
			return objectId.equals(((GitResourceVariant) obj).getObjectId());

		return false;
	}

	@Override
	public String toString() {
		return path + "(" + objectId.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected abstract TreeWalk getTreeWalk(Repository repo, RevTree revTree,
			String path) throws IOException;

	protected ObjectId getObjectId() {
		return objectId;
	}

	protected Repository getRepository() {
		return repo;
	}

	protected RevCommit getRevCommit() {
		return revCommit;
	}

	protected String getPath() {
		return path;
	}

	protected IPath getFullPath() {
		if (fullPath == null)
			fullPath = new Path(path);

		return fullPath;
