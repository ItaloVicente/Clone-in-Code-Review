	private void processShallow(@Nullable List<ObjectId> shallowCommits
			List<ObjectId> unshallowCommits
			boolean writeToPckOut) throws IOException {
		if (options.contains(OPTION_DEEPEN_RELATIVE) ||
				shallowSince != 0 ||
				shallowExcludeRefs != null) {
			throw new UnsupportedOperationException();
		}

