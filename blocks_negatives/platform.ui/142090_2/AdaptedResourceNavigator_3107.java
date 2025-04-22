        }
        if (selection.size() > 1) {
            return NLS.bind(ResourceNavigatorMessages.ResourceNavigator_statusLine, Integer.valueOf(selection.size()));
        }
    }

    /**
     * Returns the tool tip text for the given element.
     */
    String getToolTipText(Object element) {
        if (element instanceof IResource) {
            IPath path = ((IResource) element).getFullPath();
            if (path.isRoot()) {
                return ResourceNavigatorMessages.ResourceManager_toolTip;
            }
