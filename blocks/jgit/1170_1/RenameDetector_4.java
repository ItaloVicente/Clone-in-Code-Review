	private void breakModifies(ProgressMonitor pm) throws IOException {
		if (modifyScore <= 0)
			return;

		pm.beginTask(JGitText.get().renamesBreakingModifies

		for (int i = 0; i < entries.size(); i++) {
			DiffEntry e = entries.get(i);
			if (e.getChangeType() == ChangeType.MODIFY) {
				if (modifyScore > 100) {
					List<DiffEntry> tmp = DiffEntry.breakModify(e);
					deleted.add(tmp.get(0));
					added.add(tmp.get(1));
					entries.remove(i);
					i--;
				} else if (!e.newId.equals(e.oldId)) {
					if (calculateModifyScore(e) < modifyScore) {
						List<DiffEntry> tmp = DiffEntry.breakModify(e);
						deleted.add(tmp.get(0));
						added.add(tmp.get(1));
						entries.remove(i);
						i--;
					}
				}
			}
			pm.update(1);
		}
	}

	private void rejoinModifies(ProgressMonitor pm) {
		HashMap<String

		pm.beginTask(JGitText.get().renamesRejoiningModifies
				+ deleted.size());

		for (DiffEntry src : deleted) {
			nameMap.put(src.oldName
			pm.update(1);
		}

		for (int i = 0; i < added.size(); i++) {
			DiffEntry dst = added.get(i);
			DiffEntry src = nameMap.get(dst.newName);
			if (src != null && sameType(src.oldMode
				entries.add(modify(src
				deleted.remove(src);
				added.remove(i);
				i--;
			}
			pm.update(1);
		}
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

