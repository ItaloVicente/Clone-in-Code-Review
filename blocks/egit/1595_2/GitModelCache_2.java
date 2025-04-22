		this(parent, baseCommit, new FileModelFactory() {

			public GitModelBlob createFileModel(
					GitModelObjectContainer modelParent, RevCommit commit,
					ObjectId repoId, ObjectId cacheId, String name)
					throws IOException {
				return new GitModelBlob(modelParent, commit, repoId, repoId,
						cacheId, name);
			}
		});
	}

	protected GitModelCache(GitModelObject parent, RevCommit baseCommit,
			FileModelFactory fileFactory) throws IOException {
