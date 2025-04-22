	private void mergeList(Map<String, DiffEntry> baseList,
			Collection<String> changedFiles,
			Map<String, DiffEntry> listForChangedFiles) {
		for (String file : changedFiles) {
			if (baseList.keySet().contains(file)) {
				if (!listForChangedFiles.keySet().contains(file))
					baseList.remove(file);
			} else {
				if (listForChangedFiles.keySet().contains(file))
					baseList.put(file, listForChangedFiles.get(file));
			}
		}
	}

	private static Set<String> mergeUntrackedFolders(
			Set<String> oldUntrackedFolders, Collection<String> changedFiles,
			Set<String> newUntrackedFolders) {
