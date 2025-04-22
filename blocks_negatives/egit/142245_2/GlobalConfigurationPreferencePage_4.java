		Collections.sort(repositories, new Comparator<Repository>() {

			@Override
			public int compare(Repository repo1, Repository repo2) {
				return String.CASE_INSENSITIVE_ORDER.compare(getName(repo1),
						getName(repo2));
			}
		});
