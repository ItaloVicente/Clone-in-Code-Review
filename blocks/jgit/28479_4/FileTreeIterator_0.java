				FileMode dirMode;
				Repository subRepo = null;
				try {
					File gitDir = new File(f
					subRepo = new RepositoryBuilder().setGitDir(gitDir)
							.setMustExist(true).build();
					subRepo.resolve(Constants.HEAD);
					dirMode = FileMode.GITLINK;
				} catch (IOException e) {
					dirMode = FileMode.TREE;
				} finally {
					if (subRepo != null)
						subRepo.close();
				}
				mode = dirMode;
