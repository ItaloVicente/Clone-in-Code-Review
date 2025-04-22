	public String simplify(final String revstr)
			throws AmbiguousObjectException
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw
			if (resolved != null)
				if (resolved instanceof String)
					return (String) resolved;
				else
					return ((AnyObjectId) resolved).getName();
			return null;
		} finally {
			rw.release();
		}
	}

	private Object resolve(final RevWalk rw
			throws IOException {
