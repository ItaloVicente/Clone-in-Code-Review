	private ObjectId processHaveLines(List<ObjectId> peerHas
			throws IOException {
		if (peerHas.isEmpty())
			return last;

		AsyncRevObjectQueue q = walk.parseAny(peerHas
