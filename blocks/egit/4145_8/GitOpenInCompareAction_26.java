		ObjectWalk ow = new ObjectWalk(repo);
		ObjectId objectId = blob.getBaseCommitId().toObjectId();
		RevCommit commit;
		try {
			commit = ow.parseCommit(objectId);
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitOpenInCompareAction_cannotRetriveCommitWithId,
					objectId, repo.getDirectory()), e);
			return null;
		}

		return CompareUtils.getFileRevisionTypedElement(gitPath, commit, repo);
	}

	private IFile getFileForBlob(GitModelBlob blob) {
		IPath blobLocation = blob.getLocation();
