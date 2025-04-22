		ObjectId objectId;
		if (cachedData.getDiffEntry() != null)
			objectId = getObjectId(cachedData.getDiffEntry());
		else
			objectId = getObjectId(gsd);
		IResourceVariant variant = null;
		if (!objectId.equals(zeroId())) {
			if (resource.getType() == IResource.FILE)
				variant = new GitRemoteFile(repo, getCommitId(gsd), objectId,
						path);
			else
				variant = new GitRemoteFolder(repo, cachedData,
						getCommitId(gsd), objectId, path);

			cache.put(resource, variant);
