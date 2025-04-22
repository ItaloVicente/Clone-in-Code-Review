		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req, String name)
					throws RepositoryNotFoundException,
					ServiceNotEnabledException {
				if (!name.equals(srcName))
					throw new RepositoryNotFoundException(name);

				final Repository db = src.getRepository();
				db.incrementOpen();
				return db;
