
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
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			repo.newObjectReader().open(oid).copyTo(result);
			return result.toByteArray();
		}
