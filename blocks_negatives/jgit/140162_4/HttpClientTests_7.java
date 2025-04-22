		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req, String name)
					throws RepositoryNotFoundException,
					ServiceNotEnabledException {
				final Repository db = remoteRepository.getRepository();
				if (!name.equals(nameOf(db)))
					throw new RepositoryNotFoundException(name);

				db.incrementOpen();
				return db;
			}
