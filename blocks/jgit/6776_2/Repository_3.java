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
		} finally {
			rw.release();
		}
	}

	private Object resolve(final RevWalk rw
			throws IOException {
