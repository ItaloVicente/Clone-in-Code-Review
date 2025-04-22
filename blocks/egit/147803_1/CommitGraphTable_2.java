				String name = commit.getId().name();
				commitsMap.put(name, commit);
				if (name.equals(topName)) {
					topIndex = commitsMap.size() - 1;
				}
			}
		}
		return topIndex;
	}

	private int findCommit(SWTCommit[] asArray, String topName) {
		int index = 0;
		for (SWTCommit commit : asArray) {
			if (commit != null) {
				String name = commit.getId().name();
				if (name.equals(topName)) {
					return index;
				}
