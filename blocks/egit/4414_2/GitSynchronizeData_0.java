		try {
			srcRevCommit = getCommit(srcRev, ow);
			dstRevCommit = getCommit(dstRev, ow);
		} finally {
			ow.release();
		}
