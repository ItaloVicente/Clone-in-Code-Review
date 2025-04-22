	private void computeShallowsAndUnshallows(Iterable<ObjectId> wantedOids
			IOConsumer<ObjectId> shallowFunc
			IOConsumer<ObjectId> unshallowFunc)
			throws IOException {
		if (options.contains(OPTION_DEEPEN_RELATIVE) || shallowSince != 0
				|| !deepenNotRefs.isEmpty()) {
