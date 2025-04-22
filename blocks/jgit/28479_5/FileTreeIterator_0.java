		private static boolean directoryIsValidGitRepo(File f) {
			Repository subRepo = null;
			try {
				File gitDir = new File(f
				if (gitDir.isDirectory()) {
					subRepo = new RepositoryBuilder().setGitDir(gitDir)
							.setMustExist(true).build();
					subRepo.resolve(Constants.HEAD);
					return true;
				} else
					return false;
			} catch (IOException e) {
				return false;
			} finally {
				if (subRepo != null)
					subRepo.close();
			}
		}

