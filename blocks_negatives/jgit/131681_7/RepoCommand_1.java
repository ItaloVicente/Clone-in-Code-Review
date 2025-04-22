		/**
		 * Read a file from the repository
		 *
		 * @param repo
		 *            The repository containing the file
		 * @param ref
		 *            The ref (branch/tag/etc.) to read
		 * @param path
		 *            The relative path (inside the repo) to the file to read
		 * @return the file's content
		 * @throws GitAPIException
		 * @throws IOException
		 * @since 3.5
		 */
		protected byte[] readFileFromRepo(Repository repo,
				String ref, String path) throws GitAPIException, IOException {
			try (ObjectReader reader = repo.newObjectReader()) {
				return reader.open(oid).getBytes(Integer.MAX_VALUE);
