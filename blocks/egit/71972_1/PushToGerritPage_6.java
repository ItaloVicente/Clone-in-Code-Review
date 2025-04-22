	private void initializeTopic(String remoteBranch) {
		boolean enabled = false;
		String storedTopic = null;
		String branch = null;
		try {
			branch = repository.getBranch();
			if (ObjectId.isId(branch)) {
				branch = null;
			}
		} catch (final IOException e) {
			Activator.logError(e.getLocalizedMessage(), e);
		}
		if (branch != null) {
			StoredConfig config = repository.getConfig();
			enabled = config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION,
					branch, GERRIT_TOPIC_USE_KEY, false);
			storedTopic = config.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branch,
					GERRIT_TOPIC_KEY);
		}
		if (storedTopic == null || storedTopic.isEmpty()) {
			if (branch != null && !branch.isEmpty()
					&& !branch.equals(remoteBranch)) {
				topicText.setText(branch);
			}
		} else {
			topicText.setText(storedTopic);
		}
		useTopic.setSelection(enabled);
		topicText.setEnabled(enabled);
		String[] proposals = settings
				.getArray(repository + LAST_TOPICS_POSTFIX);
		if (proposals != null) {
			for (int i = proposals.length - 1; i >= 0; i--) {
				if (!proposals[i].isEmpty()) {
					topicProposals.put(proposals[i], null);
				}
			}
		}
	}

