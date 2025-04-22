		@Override
		public IContentProposal getProposal() {
			String label = Long.toString(getChangeNumber()) + " - " //$NON-NLS-1$
					+ getPatchSetNumber();
			String description = MessageFormat.format(
					UIText.FetchGerritChangePage_ContentAssistDescription,
					getPatchSetNumber().toString(),
					Long.toString(getChangeNumber()));
			return new ContentProposal(getRefName(), label, description, 0);
