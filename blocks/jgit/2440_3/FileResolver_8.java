		for (File base : exportBase) {
			File dir = FileKey.resolve(new File(base
			if (dir == null)
				continue;

			try {
				FileKey key = FileKey.exact(dir
				db = RepositoryCache.open(key
			} catch (IOException e) {
				throw new RepositoryNotFoundException(name
			}

			try {
				if (isExportOk(req
					return db;
				} else
					throw new ServiceNotEnabledException();

			} catch (RuntimeException e) {
				db.close();
				throw new RepositoryNotFoundException(name

			} catch (IOException e) {
				db.close();
				throw new RepositoryNotFoundException(name

			} catch (ServiceNotEnabledException e) {
				db.close();
				throw e;
			}
