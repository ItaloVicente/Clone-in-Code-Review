			try {
				FileRepository fileRep = getGitRepository(dir);
				return fs.resolve(fileRep.getGitCommonDir()
						&& fs.resolve(fileRep.getGitCommonDir()
						&& isValidHead(
								new File(fileRep.getGitDir()
			} catch (IOException e) {
				return false;
			}
		}

		private static FileRepository getGitRepository(final File dir)
				throws IOException {
			return new FileRepository(dir);
