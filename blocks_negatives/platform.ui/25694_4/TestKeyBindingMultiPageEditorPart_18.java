    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setInput(input);
        setSite(site);
        setTitleImage(input.getImageDescriptor().createImage());
    }
