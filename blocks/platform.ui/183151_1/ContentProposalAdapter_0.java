	private boolean autoActivateOnAllModifications = false;

	public ContentProposalAdapter(Control control, IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, KeyStroke keyStroke) {
		this(control, controlContentAdapter, proposalProvider, keyStroke, null);
		this.autoActivateOnAllModifications = true;
	}

