	private List<String> getCommitFileListForRepository(
			Map<Repository, List<String>> filesByRepo, Repository repository) {
		List<String> result = filesByRepo.get(repository);
		if (result == null) {
			result = new ArrayList<String>();
			filesByRepo.put(repository, result);
		}
		return result;
