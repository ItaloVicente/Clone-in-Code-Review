
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
					}
					putTagTargets = true;
				}
