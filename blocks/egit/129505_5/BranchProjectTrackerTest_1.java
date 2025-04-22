
	private String[] getProjectPaths() {
		try {
			String branch = repository.getBranch();
			return getProjectPaths(branch);
		} catch (IOException e) {
			return new String[0];
		}
	}

	private String[] getProjectPaths(String branch) {
		List<String> value = ProjectTrackerPreferenceHelper
				.restoreFromPreferences(repository, branch);
		return value.toArray(new String[0]);
	}
