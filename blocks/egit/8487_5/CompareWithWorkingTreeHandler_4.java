			Repository repo = getRepository(event);
			ObjectId headCommit;
			try {
				headCommit = repo.resolve(Constants.HEAD);
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
