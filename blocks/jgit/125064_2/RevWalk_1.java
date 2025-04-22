		for (ObjectId id : reader.getShallowCommits()) {
			if (id.equals(rc.getId())) {
				rc.parents = RevCommit.NO_PARENTS;
			} else {
				lookupCommit(id).parents = RevCommit.NO_PARENTS;
			}
		}
