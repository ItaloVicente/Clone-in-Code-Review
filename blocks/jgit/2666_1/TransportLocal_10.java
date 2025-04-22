		@Override
		public boolean canHandle(Repository local
			if (uri.getPath() == null
					|| uri.getPort() > 0
					|| uri.getUser() != null
					|| uri.getPass() != null
					|| uri.getHost() != null
					|| (uri.getScheme() != null && !getSchemes().contains(uri.getScheme())))
				return false;
			return true;
		}

		@Override
		public Transport open(Repository local
				throws NoRemoteRepositoryException {
			File path = local.getFS().resolve(new File(".")
			if (path.isFile())
				return new TransportBundleFile(local

			File gitDir = RepositoryCache.FileKey.resolve(path
			if (gitDir == null)
				throw new NoRemoteRepositoryException(uri
			return new TransportLocal(local
		}
	};
