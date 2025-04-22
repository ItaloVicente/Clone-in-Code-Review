	private static String findRef(String ref, Repository repo)
			throws IOException {
		if (!ObjectId.isId(ref)) {
			if (r != null)
				return r.getName();
		}
		return ref;
	}
