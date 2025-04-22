	private boolean asyncProposalComputation = false;

	public ContentProposalAdapter(Control control, IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, KeyStroke keyStroke, char[] autoActivationCharacters,
			boolean asyncProposalComputation) {
		this(control, controlContentAdapter, proposalProvider, keyStroke, autoActivationCharacters);
		this.asyncProposalComputation = asyncProposalComputation;
	}

