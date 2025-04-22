
	private void addKeyContentProposal() {
		Map<String, ConfigEntryProposal> configEntryProposals = ConfigEntryProposal
				.createAllProposals();
		List<String> keys = new ArrayList<>(configEntryProposals.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		UIUtils.addContentProposalToText(keyText, () -> keys,
				(pattern, possibleKey) -> {
					if (pattern != null
							&& !pattern.matcher(possibleKey).matches()) {
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

	private void addValueContentProposal() {
		UIUtils.addContentProposalToText(valueText, () -> {
			ConfigEntryProposal configEntryProposal = ConfigEntryProposal
					.createAllProposals().get(keyText.getText());
			if (configEntryProposal != null) {
				return configEntryProposal.getValues();
			}
			return Collections.emptyList();
		}, (pattern, possibleValue) -> {
			if (pattern != null && !pattern.matcher(possibleValue).matches()) {
				return null;
			}
			return new ContentProposal(possibleValue);
		}, null, UIText.AddConfigEntryDialog_ContentProposalStartTypingText_Values,
				UIText.AddConfigEntryDialog_ContentProposalHoverText_Values);
	}

