		{
			if (objects.contains(id)) {
				lookupCommit(id).parents = RevCommit.NO_PARENTS;
			} else {
				shallowIds.add(id);
			}
		}
