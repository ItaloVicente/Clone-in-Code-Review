
		public ReadFileResult readFile(String uri
				throws GitAPIException
			File dir = FileUtils.createTempDir("jgit_"
			Git git = Git
					.cloneRepository()
					.setDirectory(dir)
					.setURI(uri)
					.call();
			git.checkout()
					.setName(Constants.DEFAULT_REMOTE_NAME + "/" + ref).call();
			File file = new File(dir
			return new ReadFileResult(file.length()
		}
