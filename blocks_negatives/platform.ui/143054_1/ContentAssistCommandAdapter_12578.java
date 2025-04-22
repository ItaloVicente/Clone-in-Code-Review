	public ContentAssistCommandAdapter(Control control,
			IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, String commandId,
			char[] autoActivationCharacters) {
		this(control, controlContentAdapter, proposalProvider, commandId,
				autoActivationCharacters, false);
