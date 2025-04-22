	private static final int BASE_NTH = 0;

	private static final int REMOTE_NTH = 1;

	protected interface FileModelFactory {
		GitModelBlob createFileModel(GitModelObjectContainer parent,
				RevCommit commit, ObjectId repoId, ObjectId cacheId, String name)
				throws IOException;
	}

