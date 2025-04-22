			RevCommit leftCommit;
			try (RevWalk rw = new RevWalk(repo)) {
				leftCommit = rw.parseCommit(repo
						.resolve(Constants.HEAD));
			} catch (Exception e) {
				throw new ExecutionException(e.getMessage(), e);
