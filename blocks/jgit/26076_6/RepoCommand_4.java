
	private static String findRef(String ref
			throws IOException {
		if (!ObjectId.isId(ref)) {
			Ref r = repo.getRef(
					Constants.DEFAULT_REMOTE_NAME + "/" + ref);
			if (r != null)
				return r.getName();
		}
		return ref;
	}
