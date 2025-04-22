		Repository repo = map.getRepository();
		GitIndex index = null;
		index = repo.getIndex();
		Entry entry = index.getEntry(map.getRepoRelativePath(file));
		if (entry == null)
			return;
		if (indexesWithUnmergedEntries.contains(index)) {
			notAddedFiles.add(file);
			return;
		} else {
			if (!canUpdateIndex(index)) {
				indexesWithUnmergedEntries.add(index);
				notAddedFiles.add(file);
				return;
			}
		}
		if (entry.isModified(map.getWorkDir())) {
			entry.update(new File(map.getWorkDir(), entry.getName()));
			if (!changedIndexes.contains(index))
				changedIndexes.add(index);
		}
	}

	/**
	 * The method checks if the given index can be updated. The index can be
	 * updated if it does not contain entries with stage !=0.
	 *
	 * @param index
	 * @return true if the given index can be updated
	 */
	private static boolean canUpdateIndex(GitIndex index) {
		Entry[] members = index.getMembers();
		for (int i = 0; i < members.length; i++) {
			if (members[i].getStage() != 0)
				return false;
