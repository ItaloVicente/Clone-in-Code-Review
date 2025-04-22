					try {
						GitModelSynchronize.synchronizeModelWithWorkspace(file,
								repo, commit.getName());
					} catch (IOException e) {
						throw new ExecutionException(e.getMessage(), e);
					}
				}
			}
			if (input instanceof File) {
				File file = (File) input;
				RevCommit leftCommit;
				RevWalk walk = new RevWalk(repo);
				try {
					leftCommit = walk.parseCommit(headCommit);
				} catch (Exception e) {
					throw new ExecutionException(e.getMessage(), e);
				} finally {
					walk.release();
