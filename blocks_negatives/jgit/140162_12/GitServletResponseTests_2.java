		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req, String name)
					throws RepositoryNotFoundException,
					ServiceNotEnabledException {
				if (!name.equals(repoName))
					throw new RepositoryNotFoundException(name);

				final Repository db = srv.getRepository();
				db.incrementOpen();
				return db;
