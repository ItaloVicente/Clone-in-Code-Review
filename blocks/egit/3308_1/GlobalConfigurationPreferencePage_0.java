	private void sortRepositoriesByName() {
		Collections.sort(repositories, new Comparator<Repository>() {

			public int compare(Repository repo1, Repository repo2) {
				String repo1Name = repo1.getDirectory().getParentFile().getName();
				String repo2Name = repo2.getDirectory().getParentFile().getName();
				return repo1Name.compareTo(repo2Name);
			}
		});
	}

