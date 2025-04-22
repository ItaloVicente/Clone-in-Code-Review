		try {
			if (isExportOk(req, repositoryName, db)) {
				return db;
			} else
				throw new ServiceNotEnabledException();

		} catch (RuntimeException e) {
			db.close();
			throw new RepositoryNotFoundException(repositoryName, e);

		} catch (IOException e) {
			db.close();
			throw new RepositoryNotFoundException(repositoryName, e);

		} catch (ServiceNotEnabledException e) {
			db.close();
			throw e;
