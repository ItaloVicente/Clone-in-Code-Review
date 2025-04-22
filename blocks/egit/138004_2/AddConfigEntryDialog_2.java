
	private void addKeyContentProposal(final Text textField) {
		Map<String, ConfigEntryProposal> configEntryProposals = ConfigEntryProposal
				.createAllProposals();
		List<String> keys = new ArrayList<>(configEntryProposals.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		UIUtils.<String> addContentProposalToText(textField, () -> keys,
				(pattern, possibleKey) -> {
					if (pattern != null && !pattern.matcher(possibleKey).matches()) {
						return null;
					}
					ConfigEntryProposal configEntryProposal = configEntryProposals
							.get(possibleKey);
					return new ContentProposal(configEntryProposal.getKey(),
							configEntryProposal.getDescription());
				}, null,
				UIText.AddConfigEntryDialog_ContentProposalStartTypingText_Keys,
				UIText.AddConfigEntryDialog_ContentProposalHoverText_Keys);
	}

	private void addValueContentProposal(final Text textField,
			final String selectedKey) {
		ConfigEntryProposal configEntryProposal = ConfigEntryProposal
				.createAllProposals()
				.get(selectedKey);
		List<String> values;
		if (configEntryProposal != null) {
			values = configEntryProposal.getValues();
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
				UIText.AddConfigEntryDialog_ContentProposalStartTypingText_Values,
				UIText.AddConfigEntryDialog_ContentProposalHoverText_Values);
	}

