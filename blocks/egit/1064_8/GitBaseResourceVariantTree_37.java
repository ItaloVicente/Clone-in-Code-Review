		try {
			Ref srcRef = repo.getRef(gsd.getSrcRev());
			Ref dstRef = repo.getRef(gsd.getDstRev());

			RevCommit srcRev = rw.parseCommit(srcRef.getObjectId());
			RevCommit dstRev = rw.parseCommit(dstRef.getObjectId());

			rw.markStart(dstRev);
			rw.markStart(srcRev);

			result = rw.next();
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}

		return result != null ? result : null;
