		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req, String name)
					throws RepositoryNotFoundException,
					ServiceNotEnabledException {
				if (!name.equals(nameOf(db)))
					throw new RepositoryNotFoundException(name);

				db.incrementOpen();
				return db;
			}
