		gs.setRepositoryResolver((HttpServletRequest req
			if (!name.equals(srcName))
				throw new RepositoryNotFoundException(name);

			final Repository db = src.getRepository();
			db.incrementOpen();
			return db;
