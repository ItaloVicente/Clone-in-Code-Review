	private void initializeRevWalk(RevWalk rw, RevFlag srcFlag, RevFlag dstFlag)
			throws IOException {
		RevCommit srcCommit = rw.parseCommit(gsd.getSrcRevCommit());
		srcCommit.add(srcFlag);

		RevCommit dstCommit = rw.parseCommit(gsd.getDstRevCommit());
		dstCommit.add(dstFlag);

		rw.markStart(srcCommit);
		rw.markStart(dstCommit);

		rw.carry(srcFlag);
		rw.carry(dstFlag);
	}

