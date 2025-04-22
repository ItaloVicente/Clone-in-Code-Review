	public Ref peel(final Ref ref) {
		try {
			return getRefDatabase().peel(ref);
		} catch (IOException e) {
			return ref;
		}
	}
