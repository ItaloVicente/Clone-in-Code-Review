	public void init(IEditorSite site, MultiEditorInput input) throws PartInitException {
		setInput(input);
		setSite(site);
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
		setupEvents();
	}

	@Override
