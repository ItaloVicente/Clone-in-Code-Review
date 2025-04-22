		RevisionInformation info;
		ObjectId currentHead = null;
		if (startCommit != null) {
			info = computeRevisions(repository, startCommit, path,
					progress.newChild(2));
		} else {
