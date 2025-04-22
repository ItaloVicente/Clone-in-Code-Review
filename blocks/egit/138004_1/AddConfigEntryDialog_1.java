
	private void addKeyContentProposal(final Text textField) {
		Map<String, ConfigProposal> configProposals = createAllProposals();
		List<String> keys = new ArrayList<>(configProposals.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		UIUtils.<String> addContentProposalToText(textField, () -> keys,
				(pattern, possibleKey) -> {
					if (pattern != null && !pattern.matcher(possibleKey).matches()) {
						return null;
					}
					ConfigProposal configProposal = configProposals.get(possibleKey);
					return new ContentProposal(configProposal.key,
							configProposal.description);
				}, null,
				UIText.AddConfigEntryDialog_ContentProposalStartTypingText,
				UIText.AddConfigEntryDialog_ContentProposalHoverText);
	}

	private void addValueContentProposal(final Text textField,
			final String selectedKey) {
		ConfigProposal configProposal = createAllProposals().get(selectedKey);
		List<String> values;
		if (configProposal != null) {
			values = configProposal.values;
		} else {
			values = Collections.emptyList();
		}
		UIUtils.<String> addContentProposalToText(textField, () -> values,
				(pattern, possibleValue) -> {
					if (pattern != null && !pattern.matcher(possibleValue).matches()) {
						return null;
					}
					return new ContentProposal(possibleValue);
				}, null,
				UIText.AddConfigEntryDialog_ContentProposalStartTypingText,
				UIText.AddConfigEntryDialog_ContentProposalHoverText);
	}

	private static Map<String, ConfigProposal> createAllProposals() {
		List<ConfigProposal> proposals = Arrays.asList(new ConfigProposal(
				"branch.autosetuprebase", //$NON-NLS-1$
				"When a new branch is created, this configures the branch to use rebase instead of merge, when pulling.", //$NON-NLS-1$
				Arrays.asList("always", "never", "local", "remote")), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				new ConfigProposal("user.email", //$NON-NLS-1$
						"Your email address. Will be used as author email address and committer email address in new commits.", //$NON-NLS-1$
						Collections.emptyList()),
				new ConfigProposal("user.name", //$NON-NLS-1$
						"Your user name. Will be used as author and committer in new commits.", //$NON-NLS-1$
						Collections.emptyList()),
				new ConfigProposal("fetch.prune", //$NON-NLS-1$
						"Always prune the repository when fetching or pulling.", //$NON-NLS-1$
						Arrays.asList("true"))); //$NON-NLS-1$
		return proposals.stream()
				.collect(Collectors.toMap(configProposal -> configProposal.key,
						configProposal -> configProposal));
	}

	private static class ConfigProposal {
		public ConfigProposal(String key, String description,
				List<String> values) {
			this.key = key;
			this.description = description;
			this.values = values;
		}

		private final String key;

		private final String description;

		private final List<String> values;
	}
