	private static DiffEntry bestPathMatch(DiffEntry src
		DiffEntry best = null;
		int score = -1;

		for (DiffEntry d : list) {
			if (sameType(mode(d)
				int tmp = SimilarityRenameDetector
						.nameScore(path(d)
				if (tmp > score) {
					best = d;
					score = tmp;
				}
			}
		}

		return best;
	}

	@SuppressWarnings("unchecked")
	private HashMap<AbbreviatedObjectId
			List<DiffEntry> diffEntries
		HashMap<AbbreviatedObjectId
		for (DiffEntry de : diffEntries) {
			Object old = map.put(id(de)
			if (old instanceof DiffEntry) {
				ArrayList<DiffEntry> list = new ArrayList<DiffEntry>(2);
				list.add((DiffEntry) old);
				list.add(de);
				map.put(id(de)
			} else if (old != null) {
				((List<DiffEntry>) old).add(de);
				map.put(id(de)
			}
			pm.update(1);
		}
		return map;
	}

	private static String path(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldName : de.newName;
	}

	private static FileMode mode(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldMode : de.newMode;
	}

	private static AbbreviatedObjectId id(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldId : de.newId;
	}

