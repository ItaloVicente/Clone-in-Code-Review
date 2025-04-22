
	@Override
	public ISchedulingRule getSchedulingRule() {
		if (isHead) {
			return null;
		} else {
			return super.getSchedulingRule();
		}
	}

	private static boolean isHead(final GitFlowRepository gfRepo, final String sha1) {
		try {
			RevCommit head = gfRepo.findHead();
			return sha1.equals(head.getName());
		} catch (WrongGitFlowStateException e) {
			return false;
		}
	}

	private static String findHead(GitFlowRepository repository) {
		try {
			return repository.findHead().getName();
		} catch (WrongGitFlowStateException e) {
			throw new IllegalStateException(e);
		}
	}
