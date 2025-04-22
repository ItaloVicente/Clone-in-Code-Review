		RevCommit result;
		Repository repo = gsd.getRepository();
		RevWalk rw = new RevWalk(repo);
		rw.setRevFilter(RevFilter.MERGE_BASE);

		try {
			Ref dstRef = gsd.getDstRev();
			if (dstRef == null)
				result = null;
			else
				result = rw.parseCommit(dstRef.getObjectId());
		} catch (IOException e) {
			throw new TeamException("", e); //$NON-NLS-1$
		}

		return result != null ? result : null;
