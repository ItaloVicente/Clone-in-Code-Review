	private static List<Ref> findBranchesReachableFrom(RevCommit commit,
			RevWalk revWalk, Collection<Ref> refs)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {

		List<Ref> result = new ArrayList<Ref>();
		revWalk.markStart(Arrays.asList(commit.getParents()));
		ObjectIdSubclassMap<ObjectId> cutOff = new ObjectIdSubclassMap<ObjectId>();


		for (Ref ref : refs) {
			RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());

			if (headCommit.getCommitTime() + SKEW < commit.getCommitTime())
				continue;

			revWalk.resetRetain();
			revWalk.markStart(headCommit);
			RevCommit current;
			Ref found = null;
			while ((current = revWalk.next()) != null) {
				if (AnyObjectId.equals(current, commit)) {
					found = ref;
					break;
				}
				if (cutOff.contains(current))
					break;
				maybeCutOff.add(current.toObjectId());
			}
			if (found != null)
				result.add(ref);
			else
				for (ObjectId id : maybeCutOff)
					cutOff.addIfAbsent(id);

		}
		return result;
	}

