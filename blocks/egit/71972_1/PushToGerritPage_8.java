	private void addTopicProposal(Text textField) {
		if (topicProposals.isEmpty()) {
			return;
		}
		KeyStroke stroke = UIUtils.getKeystrokeOfBestActiveBindingFor(
				IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST);
		if (stroke != null) {
			UIUtils.addBulbDecorator(textField,
					NLS.bind(
							UIText.PushToGerritPage_TopicContentProposalHoverText,
							stroke.format()));
		}
		String[] recentTopics = topicProposals.keySet()
				.toArray(new String[topicProposals.size()]);
		Arrays.sort(recentTopics, CommonUtils.STRING_ASCENDING_COMPARATOR);
		SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider(
				recentTopics);
		proposalProvider.setFiltering(true);
		ContentProposalAdapter adapter = new ContentProposalAdapter(textField,
				new TextContentAdapter(), proposalProvider, stroke, null);
		adapter.setProposalAcceptanceStyle(
				ContentProposalAdapter.PROPOSAL_REPLACE);
	}

