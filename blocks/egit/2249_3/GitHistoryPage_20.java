	private void markStartAllRefs(String prefix) throws IOException,
			MissingObjectException, IncorrectObjectTypeException {
		for (Entry<String, Ref> refEntry : input.getRepository()
				.getRefDatabase().getRefs(prefix).entrySet()) {
			Ref ref = refEntry.getValue();
			if (ref.isSymbolic())
				continue;
			currentWalk.markStart(currentWalk.parseCommit(ref.getObjectId()));
		}
	}

	private void cancelRefreshJob() {
		if (job != null && job.getState() != Job.NONE) {
			job.cancel();
			try {
				job.join();
			} catch (InterruptedException e) {
				cancelRefreshJob();
				return;
			}
			job = null;
