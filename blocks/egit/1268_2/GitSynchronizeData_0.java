
		Ref srcRef = repo.getRef(srcRev);
		Ref dstRef = repo.getRef(dstRev);
		ObjectWalk ow = new ObjectWalk(repo);
		ow.markStart(ow.parseCommit(srcRef.getObjectId()));
		this.srcRev = ow.next();

		ow.reset();
		ow.markStart(ow.parseCommit(dstRef.getObjectId()));
		this.dstRev = ow.next();

