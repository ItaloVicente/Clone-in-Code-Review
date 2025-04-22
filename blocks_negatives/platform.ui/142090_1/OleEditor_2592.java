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

    /**
     * Validates the given input
     *
     * @param input the editor input to validate
     * @throws PartInitException if the editor input is not OK
     */
    private boolean validatePathEditorInput(IEditorInput input) throws PartInitException {
