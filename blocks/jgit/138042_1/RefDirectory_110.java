	public Ref exactRef(String name) throws IOException {
		try {
			return readAndResolve(name
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	@NonNull
	public Map<String
		try {
			RefList<Ref> packed = getPackedRefs();
			Map<String
			for (String name : refs) {
				Ref ref = readAndResolve(name
