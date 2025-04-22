			if (headCommit.getCommitTime() + SKEW < commit.getCommitTime())
				continue;

			List<ObjectId> maybeCutOff = new ArrayList<ObjectId>(cutOff.size()); // guess rough size
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
