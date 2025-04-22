	private void mergeList(Set<String> baseList,
			Collection<String> changedFiles, Set<String> listForChangedFiles,
			Map<String, StageState> baseStates,
			Map<String, StageState> newConflictStates) {
		for (String file : changedFiles) {
			if (baseList.contains(file)) {
				if (!listForChangedFiles.contains(file)) {
					baseList.remove(file);
					baseStates.remove(file);
				} else {
					StageState state = newConflictStates.get(file);
					if (state != null) {
						baseStates.put(file, state);
					}
				}
			} else {
				if (listForChangedFiles.contains(file)) {
					baseList.add(file);
					StageState state = newConflictStates.get(file);
					if (state != null) {
						baseStates.put(file, state);
					}
				}
			}
		}
	}

