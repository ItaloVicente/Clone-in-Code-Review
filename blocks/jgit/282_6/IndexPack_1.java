					if (needBaseObjectIds) {
						Iterator<DeltaChain> iter = baseById.iterator();
						baseIds = new HashSet<ObjectId>();
						while (iter.hasNext()) {
							baseIds.add(iter.next());
						}
					}
