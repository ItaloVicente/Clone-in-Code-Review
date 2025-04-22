		switchRepositoriesAction = new RepositoryToolbarAction(false,
				() -> realRepository,
				repo -> {
					if (realRepository != repo) {
						reload(repo);
					}
				});
		toolbar.add(switchRepositoriesAction);

