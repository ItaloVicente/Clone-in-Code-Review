	private RevCommitList<RevCommit> getRevCommits(AnyObjectId since, AnyObjectId until) {
		Git git = new Git(repo);
		RevCommitList<RevCommit> result = new RevCommitList<RevCommit>();
		try {
			Iterable<RevCommit> call = git.log().addRange(since, until).call();

			for (RevCommit commit : call)
				result.add(commit);

		} catch (NoHeadException e) {
			Activator.logError(e.getMessage(), e);
		} catch (JGitInternalException e) {
			Activator.logError(e.getMessage(), e);
		} catch (MissingObjectException e) {
			Activator.logError(e.getMessage(), e);
		} catch (IncorrectObjectTypeException e) {
			Activator.logError(e.getMessage(), e);
		}

		return result;
	}

