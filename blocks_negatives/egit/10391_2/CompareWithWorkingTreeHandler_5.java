		if (selection.size() == 1) {
			Iterator<?> it = selection.iterator();
			RevCommit commit = (RevCommit) it.next();
			Object input = getPage().getInputInternal().getSingleFile();
			Repository repo = getRepository(event);
			ObjectId headCommit;
			try {
				headCommit = repo.resolve(Constants.HEAD);
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
