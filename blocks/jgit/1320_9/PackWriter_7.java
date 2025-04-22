				for (int i = 0; i < cmit.getParentCount(); i++) {
					RevCommit p = cmit.getParent(i);
					if (!p.has(added) && !p.has(RevFlag.UNINTERESTING)) {
						p.add(added);
						addObject(p
						commitCnt++;
					}
				}

				if (!putTagTargets && 4096 < commitCnt) {
					for (ObjectId id : tagTargets) {
						RevObject obj = walker.lookupOrNull(id);
						if (obj instanceof RevCommit
								&& obj.has(include)
								&& !obj.has(RevFlag.UNINTERESTING)
								&& !obj.has(added)) {
							obj.add(added);
							addObject(obj
						}
