
			RepositorySelectionPage sp = new RepositorySelectionPage(false,
					null, myConfiguration.getString(RepositoriesView.REMOTE,
							null, RepositoriesView.URL));
			addPage(sp);

			RefSpecPage rsp = new RefSpecPage(repository, false, sp);
			addPage(rsp);

			sp = new RepositorySelectionPage(true, null, myConfiguration
					.getString(RepositoriesView.REMOTE, null,
							RepositoriesView.PUSHURL));
			addPage(sp);

			rsp = new RefSpecPage(repository, true, sp);
			addPage(rsp);

