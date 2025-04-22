	private GitModelObject extractFromCache(TreeWalk tw) throws IOException {
		if (shouldIncludeEntry(tw)) {
			String path = tw.getPathString();
			ObjectId repoId = tw.getObjectId(BASE_NTH);
			ObjectId cacheId = tw.getObjectId(REMOTE_NTH);

				return handleCacheTree(repoId, cacheId, path);

			return fileFactory.createFileModel(this, baseCommit, repoId,
					cacheId, getLocation().append(path));
		}
