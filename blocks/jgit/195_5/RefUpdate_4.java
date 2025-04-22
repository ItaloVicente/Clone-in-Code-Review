	public Result link(String target) throws IOException {
		if (!target.startsWith(Constants.R_REFS))
			throw new IllegalArgumentException("Not " + Constants.R_REFS);
		if (getRefDatabase().isNameConflicting(getName()))
			return Result.LOCK_FAILURE;
		try {
			if (!tryLock(false))
				return Result.LOCK_FAILURE;

			final Ref old = getRefDatabase().getRef(getName());
			if (old != null && old.isSymbolic()) {
				final Ref dst = old.getTarget();
				if (target.equals(dst.getName()))
					return result = Result.NO_CHANGE;
			}

			if (old != null && old.getObjectId() != null)
				setOldObjectId(old.getObjectId());

			final Ref dst = getRefDatabase().getRef(target);
			if (dst != null && dst.getObjectId() != null)
				setNewObjectId(dst.getObjectId());

			return result = doLink(target);
		} catch (IOException x) {
			result = Result.IO_FAILURE;
			throw x;
		} finally {
			unlock();
		}
	}

