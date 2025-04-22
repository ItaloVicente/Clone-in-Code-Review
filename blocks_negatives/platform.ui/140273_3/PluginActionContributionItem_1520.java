        if (WorkbenchActivityHelper.isFiltering() && identifier == null) {
            hookListeners();
            invalidateParent();
        } else if (!WorkbenchActivityHelper.isFiltering() && identifier != null) {
            unhookListeners();
            disposeIdentifier();
            invalidateParent();
        }
    }

    /*
     * For testing purposes only
     */
    public ISelection getSelection() {
    	return ((PluginAction)getAction()).getSelection();
    }
