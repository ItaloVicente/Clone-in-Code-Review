				if (needNewObjectIds) {
					newObjectIds = new HashSet<ObjectId>();
					for (int i = 0; i < entryCount; i++) {
						newObjectIds.add(entries[i]);
					}
				}
				if (needBaseObjectIds) {
					Iterator<DeltaChain> iter = baseById.iterator();
					baseIds = new HashSet<ObjectId>();
					while (iter.hasNext()) {
						baseIds.add(iter.next());
					}
				}
