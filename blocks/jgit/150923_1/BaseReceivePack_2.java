	private Set<ObjectId> extractAdvertisedParentCommits(
			Set<ObjectId> newRefs) throws MissingObjectException
		Set<ObjectId> advertisedParents = new HashSet<>();
		try (RevWalk ow = new RevWalk(db)) {
			for (ObjectId newRef : newRefs) {
				RevObject object = ow.parseAny(newRef);
				if (object instanceof RevCommit) {
					for (RevCommit parentCommit :
						((RevCommit) object).getParents()) {
						if (advertisedHaves.contains(parentCommit.getId())) {
							advertisedParents.add(parentCommit.getId());
						}
					}
				}
			}
		}
		return advertisedParents;
