		RevWalk rw = new RevWalk(repo);
		RevCommit dstRev = rw.lookupCommit(dstRef.getObjectId());

		rw.setRevFilter(RevFilter.MERGE_BASE);
		rw.markStart(dstRev);

		return rw.next();
