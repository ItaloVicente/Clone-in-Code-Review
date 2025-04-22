	@Override
	public void init(IEditorSite site, IEditorInput input) {
		setInput(input);
		setSite(site);
		setPartName("Editor"); //$NON-NLS-1$
		setTitleImage(input.getImageDescriptor().createImage());
		setTitleToolTip("Moooooo"); //$NON-NLS-1$
	}
