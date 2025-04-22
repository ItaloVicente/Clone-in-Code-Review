			Object resolved = resolve(rw
			if (resolved instanceof Ref) {
				return ((Ref) resolved).getObjectId();
			} else {
				return (ObjectId) resolved;
			}
		} finally {
			rw.release();
		}
	}

	public String simplify(final String revstr)
			throws AmbiguousObjectException
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw
			if (resolved != null)
				if (resolved instanceof Ref)
					return Repository
							.shortenRefName(((Ref) resolved).getName());
				else
					return ((AnyObjectId) resolved).getName();
			return null;
