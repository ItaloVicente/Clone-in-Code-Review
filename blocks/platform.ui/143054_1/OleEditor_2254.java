			throws PartInitException {

		validatePathEditorInput(input);

		setSite(site);
		setInputWithNotify(input);

		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
		ImageDescriptor desc = input.getImageDescriptor();
		if (desc != null) {
			oleTitleImage = desc.createImage();
			setTitleImage(oleTitleImage);
		}
	}

	private boolean validatePathEditorInput(IEditorInput input) throws PartInitException {
