
		public byte[] readFile(String uri
				throws GitAPIException
			File dir = FileUtils.createTempDir("jgit_"
			Repository repo = Git
					.cloneRepository()
					.setBare(true)
					.setDirectory(dir)
					.setURI(uri)
					.call()
					.getRepository();
			ObjectReader reader = repo.newObjectReader();
			byte[] result;
			try {
				result = reader.open(oid).getBytes(Integer.MAX_VALUE);
			} finally {
				reader.release();
				FileUtils.delete(dir
			}
			return result;
		}
