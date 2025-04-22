		};

		ExplicitContentProposalAdapter adapter = new ExplicitContentProposalAdapter(
				textField, cp, stroke);
		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		return adapter;
	}

	private static class ExplicitContentProposalAdapter
			extends ContentProposalAdapter {

		public ExplicitContentProposalAdapter(Control control,
				IContentProposalProvider proposalProvider,
				KeyStroke keyStroke) {
			super(control, new TextContentAdapter(), proposalProvider,
					keyStroke, null);
		}

		@Override
		public void openProposalPopup() {
			super.openProposalPopup();
		}
