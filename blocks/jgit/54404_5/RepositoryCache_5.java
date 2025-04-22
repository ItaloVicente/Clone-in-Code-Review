			try {
				FileRepository fileRep = getGitRepository(dir);
				return fs.resolve(fileRep.getGitDir(true)
						&& fs.resolve(fileRep.getGitDir(true)
						&& isValidHead(new File(fileRep.getGitDir(false)
								Constants.HEAD));
			} catch (IOException e) {
				return false;
			}
		}

		private static FileRepository getGitRepository(final File dir)
				throws IOException {
			return new FileRepository(dir);
