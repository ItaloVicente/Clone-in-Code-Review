	private void processShallow(List<ObjectId> shallowCommits) throws IOException {
		if (options.contains(OPTION_DEEPEN_RELATIVE)
				|| shallowSince != 0 || shallowExcludeRefs != null) {
			throw new UnsupportedOperationException();
		}

