	public static class ExplicitContentProposalAdapter
			extends ContentProposalAdapter {

		public ExplicitContentProposalAdapter(Control control,
				IControlContentAdapter controlContentAdapter,
				IContentProposalProvider proposalProvider,
				KeyStroke keyStroke, char[] autoActivationCharacters) {
			super(control, controlContentAdapter, proposalProvider, keyStroke,
					autoActivationCharacters);
		}

		@Override
		public void openProposalPopup() {
			super.openProposalPopup();
		}
	}

