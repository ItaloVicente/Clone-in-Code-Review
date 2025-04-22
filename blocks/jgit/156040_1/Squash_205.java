		try {
			final ObjectId id = git.getRef(branch).getObjectId();
			final Spliterator<RevCommit> log = git._log().add(id).call().spliterator();
			return stream(log
					.orElseThrow(() -> new GitException("Commit is not present at branch " + branch));
		} catch (GitAPIException | MissingObjectException | IncorrectObjectTypeException e) {
			throw new GitException("A problem occurred when trying to get commit list"
		}
	}
