	private String validateTopic(String topic) {
		if (WHITESPACE.matcher(topic).find()) {
			return UIText.PushToGerritPage_TopicHasWhitespace;
		}
		if (topic.indexOf(',') >= 0) {
			if (topic.indexOf('%') >= 0) {
				return UIText.PushToGerritPage_TopicInvalidCharacters;
			}
			String withTopic = branchText.getText().trim();
			int i = withTopic.indexOf('%');
			if (i >= 0) {
				withTopic = withTopic.substring(0, i);
			}
			withTopic += '/' + topic;
			if (knownRemoteRefs.contains(withTopic)) {
				return NLS.bind(UIText.PushToGerritPage_TopicCollidesWithBranch,
						withTopic);
			}
		}
		return null;
	}

	private String setTopicInRef(String ref, String topic) {
		String baseRef;
		String options;
		int i = ref.indexOf('%');
		if (i >= 0) {
			baseRef = ref.substring(0, i);
			options = ref.substring(i + 1);
			options = options.replaceAll("topic=[^,]*", ""); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			baseRef = ref;
			options = ""; //$NON-NLS-1$
		}
		if (topic.indexOf(',') >= 0) {
			baseRef += '/' + topic;
		} else {
			if (!options.isEmpty()) {
				options += ',';
			}
			options += "topic=" + topic; //$NON-NLS-1$
		}
		if (!options.isEmpty()) {
			return baseRef + '%' + options;
		}
		return baseRef;
	}

