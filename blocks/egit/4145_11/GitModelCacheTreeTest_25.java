		return new GitModelCacheTree(createModelCommit(),
				lookupRepository(leftRepoFile), location, new FileModelFactory() {
					public boolean isWorkingTree() {
						return false;
					}
					public GitModelBlob createFileModel(GitModelObjectContainer objParent,
							Repository repo, Change change, IPath fullPath) {
						return null;
					}
				});
