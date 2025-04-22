		try {
			Ref dstRef = repo.getRef(gsd.getDstRev());
			if (dstRef == null)
				result = null;
			else
				result = rw.parseCommit(dstRef.getObjectId());
		} catch (IOException e) {
			throw new TeamException("", e); //$NON-NLS-1$
		}

		return result != null ? result : null;
