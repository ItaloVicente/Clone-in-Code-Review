						try {
							ObjectId id = ObjectId.fromString(d + fName);
							if (objectsToKeep.contains(id))
								continue;
							if (indexObjects == null)
								indexObjects = listNonHEADIndexObjects();
							if (indexObjects.contains(id))
								continue;
							deletionCandidates.put(id
						} catch (IllegalArgumentException notAnObject) {
							continue;
						}
