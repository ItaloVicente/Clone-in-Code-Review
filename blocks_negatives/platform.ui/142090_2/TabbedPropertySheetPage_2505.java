     */
    public String getTitleText(ISelection selection) {
    	if (selection == null) {
    		selection = currentSelection;
    	}
    	return registry.getLabelProvider().getText(selection);
    }

    /**
     * Returns the title image for given selection. If selection is null,
     * then currentSelection is used.
     *
     * @param selection Selection whose properties title image is to be returned
     * @return Image that is used as a title image.
     * @since 3.5
     */
    public Image getTitleImage(ISelection selection) {
    	if (selection == null) {
    		selection = currentSelection;
    	}
