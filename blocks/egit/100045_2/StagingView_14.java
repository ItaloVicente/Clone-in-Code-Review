		switchRepositoriesAction = new RepositoryToolbarAction(false,
				repo -> {
					if (realRepository != repo) {
						reload(repo);
					}
				});
		toolbar.add(switchRepositoriesAction);

