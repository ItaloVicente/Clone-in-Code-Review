		if (objectsLists[Constants.OBJ_COMMIT] instanceof ArrayList) {
			ArrayList<ObjectToPack> list = (ArrayList<ObjectToPack>) objectsLists[Constants.OBJ_COMMIT];
			list.ensureCapacity(list.size() + commits.size());
		}
		for (RevCommit cmit : commits) {
			if (!cmit.has(added)) {
				cmit.add(added);
				addObject(cmit
			}

			for (int i = 0; i < cmit.getParentCount(); i++) {
				RevCommit p = cmit.getParent(i);
				if (!p.has(added) && !p.has(RevFlag.UNINTERESTING)) {
					p.add(added);
					addObject(p
				}
			}
		}
		commits = null;

