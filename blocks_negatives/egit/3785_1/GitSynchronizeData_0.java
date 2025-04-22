		if (srcRev.length() > 0)
			this.srcRevCommit = ow.parseCommit(repo.resolve(srcRev));
		else
			this.srcRevCommit = null;

		if (dstRev.length() > 0)
			this.dstRevCommit = ow.parseCommit(repo.resolve(dstRev));
		else
			this.dstRevCommit = null;
