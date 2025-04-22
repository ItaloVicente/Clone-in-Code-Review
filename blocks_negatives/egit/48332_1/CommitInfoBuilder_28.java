		RevWalk revWalk = new RevWalk(db);
		revWalk.setRetainBody(false);
		Map<String, Ref> tagsMap = db.getTags();
		Ref tagRef = null;

		for (Ref ref : tagsMap.values()) {
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			RevCommit current = revWalk.parseCommit(commit);
			RevObject any = revWalk.peel(revWalk.parseAny(ref.getObjectId()));
			if (!(any instanceof RevCommit))
				continue;
			RevCommit newTag = (RevCommit) any;
			if (newTag.getId().equals(commit))
				continue;

			if (isMergedInto(revWalk, newTag, current, searchDescendant)) {
