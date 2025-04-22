		if (parents == null) {
			Map<AnyObjectId
			if (grafts != null && grafts.size() > 0) {
				List<ObjectId> graftedParents = grafts.get(getId());
				if (graftedParents != null) {
					if (graftedParents.size() == 0)
						parents = NO_PARENTS;
					else {
						RevCommit[] pList = new RevCommit[graftedParents.size()];
						for (int i = 0; i < pList.length; ++i) {
							RevCommit parent = walk.lookupCommit(graftedParents
									.get(i));
							pList[i] = parent;
							parent.flags |= RevWalk.GRAFTED;
						}
						parents = pList;
					}
				}
			}
		}
