
		if (exportBase.size() == 1) {
			File dir = new File(exportBase.iterator().next()
			throw new RepositoryNotFoundException(name
					new RepositoryNotFoundException(dir));
		}

		throw new RepositoryNotFoundException(name);
