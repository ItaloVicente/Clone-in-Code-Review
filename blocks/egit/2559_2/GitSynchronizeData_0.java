		if (!srcRev.isEmpty())
			this.srcRev = ow.parseCommit(repo.resolve(srcRev));
		else
			this.srcRev = null;

		if (!dstRev.isEmpty())
			this.dstRev = ow.parseCommit(repo.resolve(dstRev));
		else
			this.dstRev = null;

		if (this.dstRev != null || this.srcRev != null)
			this.commonAncestorRev = getCommonAncestor(repo, this.srcRev,
					this.dstRev);
		else
			this.commonAncestorRev = null;
