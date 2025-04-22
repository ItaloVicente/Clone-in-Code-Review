        TableItem items[] = folderNames.getSelection();
        if (items.length == 1) {
            ArrayList result = new ArrayList();
            result.add(items[0].getData());
            setResult(result);
        }
        super.okPressed();
    }

    /**
     * Use this method to further filter resources.  As resources are gathered,
     * if a resource matches the current pattern string, this method will be called.
     * If this method answers false, the resource will not be included in the list
     * of matches and the resource's children will NOT be considered for matching.
     */
    protected boolean select(IResource resource) {
        return true;
    }

    /**
     * Refreshes the filtered list of resources.
     * Called when the text in the pattern text entry has changed.
     *
     * @param force if <code>true</code> a refresh is forced, if <code>false</code> a refresh only
     *   occurs if the pattern has changed
     *
     * @since 3.1
     */
    protected void refresh(boolean force) {
        if (gatherResourcesDynamically) {
            gatherResources(force);
        } else {
            filterResources(force);
        }
    }

    /**
     * A new resource has been selected. Change the contents
     * of the folder names list.
     *
     * @desc resource descriptor of the selected resource
     */
    private void updateFolders(final ResourceDescriptor desc) {
        BusyIndicator.showWhile(getShell().getDisplay(), () -> {
		    if (!desc.resourcesSorted) {
		        Collections.sort(desc.resources, (o1, o2) -> {
				    String s1 = getParentLabel((IResource) o1);
				    String s2 = getParentLabel((IResource) o2);
				    return collator.compare(s1, s2);
