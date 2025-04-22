		Ref srcRef = repo.getRef(gsd.getSrcRev());
		Ref dstRef = repo.getRef(gsd.getDstRev());

		RevWalk rw = new RevWalk(repo);
		RevCommit srcRev = rw.lookupCommit(srcRef.getObjectId());
		RevCommit dstRev = rw.lookupCommit(dstRef.getObjectId());

		rw.setRevFilter(RevFilter.MERGE_BASE);
		rw.markStart(dstRev);
		rw.markStart(srcRev);

		return rw.next();
