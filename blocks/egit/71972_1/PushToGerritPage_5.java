	private void storeLastUsedTopic(boolean enabled, String topic,
			String branch) {
		boolean isValid = validateTopic(topic) == null;
		if (topic.equals(branch)) {
			topic = null;
		} else if (topic.isEmpty()) {
			enabled = false;
		} else if (isValid) {
			topicProposals.put(topic, null);
			settings.put(repository + LAST_TOPICS_POSTFIX, topicProposals
					.keySet().toArray(new String[topicProposals.size()]));
		}
		if (branch != null && !ObjectId.isId(branch)) {
			StoredConfig config = repository.getConfig();
			if (enabled) {
				config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION, branch,
						GERRIT_TOPIC_USE_KEY, enabled);
			} else {
				config.unset(ConfigConstants.CONFIG_BRANCH_SECTION, branch,
						GERRIT_TOPIC_USE_KEY);
			}
			if (topic == null || topic.isEmpty()) {
				config.unset(ConfigConstants.CONFIG_BRANCH_SECTION, branch,
						GERRIT_TOPIC_KEY);
			} else if (isValid) {
				config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, branch,
						GERRIT_TOPIC_KEY, topic);
			}
			try {
				config.save();
			} catch (IOException e) {
				Activator.logError(
						NLS.bind(UIText.PushToGerritPage_TopicSaveFailure,
								repository),
						e);
			}
		}
	}

