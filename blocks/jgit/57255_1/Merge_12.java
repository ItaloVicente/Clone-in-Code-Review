		try (RevWalk revWalk = new RevWalk(db)) {
			ObjectId oldHeadObjectId = oldHead.getPeeledObjectId();
			if (oldHeadObjectId == null)
				oldHeadObjectId = oldHead.getObjectId();
			RevCommit oldHeadCommit = revWalk.lookupCommit(oldHeadObjectId);
			RevCommit srcCommit = revWalk.lookupCommit(src);
			return revWalk.isMergedInto(oldHeadCommit
		}
