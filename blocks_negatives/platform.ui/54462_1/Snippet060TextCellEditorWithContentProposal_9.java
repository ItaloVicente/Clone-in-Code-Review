		private void enableContentProposal(IContentProposalProvider contentProposalProvider, KeyStroke keyStroke,
				char[] autoActivationCharacters) {
			contentProposalAdapter = new ContentProposalAdapter(text, new TextContentAdapter(),
					contentProposalProvider, keyStroke, autoActivationCharacters);

			contentProposalAdapter.addContentProposalListener(new IContentProposalListener2() {

				@Override
				public void proposalPopupClosed(ContentProposalAdapter adapter) {
					popupOpen = false;
				}

				@Override
				public void proposalPopupOpened(ContentProposalAdapter adapter) {
					popupOpen = true;
				}
			});
