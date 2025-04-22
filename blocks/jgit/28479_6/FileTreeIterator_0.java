		private static boolean directoryIsValidGitRepo(File f) {
			Repository subRepo = null;
			try {
				File dotGit = new File(f
				subRepo = new RepositoryBuilder().setGitDir(dotGit)
						.setMustExist(true).build();
				subRepo.resolve(Constants.HEAD);
				return true;
			} catch (IOException e) {
				return false;
			} finally {
				if (subRepo != null)
					subRepo.close();
			}
		}

