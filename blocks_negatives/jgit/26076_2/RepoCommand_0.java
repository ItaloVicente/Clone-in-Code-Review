		/**
		 * Read a remote repository's HEAD sha1.
		 *
		 * @param uri
		 *            The URI of the remote repository
		 * @return the sha1 of the HEAD of the remote repository
		 */
		public ObjectId sha1(String uri) throws GitAPIException;
