		final AbbreviatedObjectId commitId = getAbbreviatedObjectId(commit);
		final AbbreviatedObjectId parentCommitId = getAbbreviatedObjectId(parentCommit);

		MutableObjectId idBuf = new MutableObjectId();
		while (tw.next()) {
			Change change = new Change();
			change.commitId = commitId;
			change.remoteCommitId = parentCommitId;
			change.name = tw.getNameString();
			tw.getObjectId(idBuf, commitIndex);
			change.objectId = AbbreviatedObjectId.fromObjectId(idBuf);
			tw.getObjectId(idBuf, parentCommitIndex);
			change.remoteObjectId = AbbreviatedObjectId.fromObjectId(idBuf);

			calculateAndSetChangeKind(direction, change);

			result.put(tw.getPathString(), change);
