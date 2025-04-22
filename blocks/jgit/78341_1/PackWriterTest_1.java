					throws IOException {
		RevWalk walk = new RevWalk(repo);
		return writePack(repo
	}

	private static PackIndex writeShallowPack(FileRepository repo
			Set<? extends ObjectId> want
			Set<? extends ObjectId> shallow) throws IOException {
		DepthWalk.RevWalk walk = new DepthWalk.RevWalk(repo
		walk.assumeShallow(shallow);
		return writePack(repo
				Collections.<ObjectIdSet> emptySet());
	}

	private static PackIndex writePack(FileRepository repo
			int depth
			Set<? extends ObjectId> have
					throws IOException {
