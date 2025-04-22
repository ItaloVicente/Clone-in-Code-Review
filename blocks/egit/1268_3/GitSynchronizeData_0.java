
		Ref srcRef = repo.getRef(srcRev);
		Ref dstRef = repo.getRef(dstRev);
		ObjectWalk ow = new ObjectWalk(repo);

		this.srcRev = ow.parseCommit(srcRef.getObjectId());
		this.dstRev = ow.parseCommit(dstRef.getObjectId());

