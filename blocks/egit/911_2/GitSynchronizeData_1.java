			Tag mapTag = repo.mapTag(rev);
			if (mapTag != null)
				return mapTag.getObjId();
		} else {
			Commit mapCommit = repo.mapCommit(rev);
			if (mapCommit != null)
				return mapCommit.getCommitId();
		}
		return null;
