		IFile file = getFileForBlob(blob);
		Repository repo = RepositoryMapping.getMapping(file).getRepository();
		String gitPath = stripWorkDir(repo.getWorkTree(), blob.getLocation().toFile());
		ObjectWalk ow = new ObjectWalk(repo);
		ObjectId objectId = blob.getBaseCommitId().toObjectId();
		RevCommit commit;
		try {
			commit = ow.parseCommit(objectId);
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitOpenInCompareAction_cannotRetrieveCommitWithId,
					objectId, repo.getDirectory()), e);
			return null;
		}

		return CompareUtils.getFileRevisionTypedElement(gitPath, commit, repo);
