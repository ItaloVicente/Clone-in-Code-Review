
		public Transport open(URIish uri) throws NotSupportedException
				TransportException {
			File path = FS.DETECTED.resolve(new File(".")
			if (path.isFile())
				return new TransportBundleFile(uri

			File gitDir = RepositoryCache.FileKey.resolve(path
			if (gitDir == null)
				throw new NoRemoteRepositoryException(uri
						JGitText.get().notFound);
			return new TransportLocal(uri
		}
