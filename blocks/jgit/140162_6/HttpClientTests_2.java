		gs.setRepositoryResolver((HttpServletRequest req
			final Repository db = remoteRepository.getRepository();
			if (!name.equals(nameOf(db)))
				throw new RepositoryNotFoundException(name);

			db.incrementOpen();
			return db;
