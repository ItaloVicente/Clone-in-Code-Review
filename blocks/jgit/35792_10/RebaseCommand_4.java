	private void recordRewrite(String sha1
			throws IOException {
		rewritePending.add(sha1);
		if (!isFixupOrSquash(nextStep)) {
			final String headSHA1 = repo.resolve(Constants.HEAD).getName();
			for (String pending : rewritePending) {
				rewrittenList.append(pending + ' ' + headSHA1 + '\n');
			}
			rewritePending.clear();
		}
	}

	private boolean isFixupOrSquash(RebaseTodoLine step) {
		return step != null
				&& (step.getAction() == Action.FIXUP || step.getAction() == Action.SQUASH);
	}

