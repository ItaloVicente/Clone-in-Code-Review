	private static List<RevCommit> refsToRevCommits(RevWalk walk,
			List<Ref> refs) throws MissingObjectException, IOException {
		List<ObjectId> objIds = refs.stream().map(
				ref -> firstNonNull(ref.getPeeledObjectId(), ref.getObjectId()))
				.collect(Collectors.toList());
		return objectIdsToRevCommits(walk, objIds);
	}
