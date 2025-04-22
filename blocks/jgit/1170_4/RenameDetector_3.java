	private void breakModifies(ProgressMonitor pm) throws IOException {
		if (breakScore <= 0)
			return;

		ArrayList<DiffEntry> newEntries = new ArrayList<DiffEntry>(entries.size());

		pm.beginTask(JGitText.get().renamesBreakingModifies

		for (int i = 0; i < entries.size(); i++) {
			DiffEntry e = entries.get(i);
			if (e.getChangeType() == ChangeType.MODIFY) {
				final int score = calculateModifyScore(e);
				if (score < breakScore) {
					List<DiffEntry> tmp = DiffEntry.breakModify(e);
					DiffEntry del = tmp.get(0);
					del.score = score;
					deleted.add(del);
					added.add(tmp.get(1));
				} else {
					newEntries.add(e);
				}
			}
			pm.update(1);
		}

		entries = newEntries;
	}

	private void rejoinModifies(ProgressMonitor pm) {
		HashMap<String
		ArrayList<DiffEntry> newAdded = new ArrayList<DiffEntry>(added.size());

		pm.beginTask(JGitText.get().renamesRejoiningModifies
				+ deleted.size());

		for (DiffEntry src : deleted) {
			nameMap.put(src.oldName
			pm.update(1);
		}

		for (DiffEntry dst : added) {
			DiffEntry src = nameMap.get(dst.newName);
			if (src != null && sameType(src.oldMode
				entries.add(DiffEntry.pair(ChangeType.MODIFY
						src.score));
				nameMap.remove(src.oldName);
			} else {
				newAdded.add(dst);
			}
			pm.update(1);
		}

		added = newAdded;
		deleted = new ArrayList<DiffEntry>(nameMap.values());
	}

	private int calculateModifyScore(DiffEntry d) throws IOException {
		SimilarityIndex src = new SimilarityIndex();
		src.hash(repo.openObject(d.oldId.toObjectId()));
		src.sort();
		SimilarityIndex dst = new SimilarityIndex();
		dst.hash(repo.openObject(d.newId.toObjectId()));
		dst.sort();
		return src.score(dst
	}

