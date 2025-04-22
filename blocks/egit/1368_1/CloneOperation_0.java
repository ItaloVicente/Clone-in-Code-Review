		final RevWalk rw = new RevWalk(local);
		final RevCommit mapCommit;
		try {
			mapCommit = rw.parseCommit(head.getObjectId());
		} finally {
			rw.release();
		}

