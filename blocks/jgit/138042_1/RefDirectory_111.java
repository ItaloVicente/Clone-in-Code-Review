			}
			return result;
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	@Nullable
	public Ref firstExactRef(String... refs) throws IOException {
		try {
			RefList<Ref> packed = getPackedRefs();
			for (String name : refs) {
				Ref ref = readAndResolve(name
