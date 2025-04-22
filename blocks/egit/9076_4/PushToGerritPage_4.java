	private void configureContentProposalToReveiwerText() {
		UIUtils.addBulbDecorator(reviewersText,
				UIText.PushToGerritPage_ReviewersContentProposalText);

		ContentProposalAdapter proposalAdapter = new ContentAssistCommandAdapter(
				reviewersText, new TextContentAdapter(),
				new ReviewerProposalProvider(new FakePersonProvider()), null,
				null, false);
		proposalAdapter.setPropagateKeys(true);
		proposalAdapter
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_IGNORE);

		proposalAdapter
				.addContentProposalListener(new IContentProposalListener() {
					public void proposalAccepted(IContentProposal proposal) {
						String content = reviewersText.getText();

						int currentPosition = reviewersText.getSelection().x;
						int nextSeparator = content.indexOf(
								REVIEWERS_SEPARATOR, currentPosition);
						int prevSeparator = currentPosition - 1;
						while (prevSeparator >= 0
								&& content.charAt(prevSeparator) != REVIEWERS_SEPARATOR) {
							prevSeparator--;
						}

						String prefix = prevSeparator < 0 ? "" : content.substring(0, prevSeparator + 1).trim(); //$NON-NLS-1$
						String suffix = nextSeparator < 0 ? "" : content.substring(nextSeparator + 1).trim(); //$NON-NLS-1$

						String proposalText = proposal.getLabel();
						reviewersText.setText(String.format("%s%s%c%s", prefix, //$NON-NLS-1$
								proposalText,
								new Character(REVIEWERS_SEPARATOR), suffix));

						int position = prefix.length() + proposalText.length()
								+ 1;
						reviewersText
								.setSelection(new Point(position, position));
					}
				});

	}

