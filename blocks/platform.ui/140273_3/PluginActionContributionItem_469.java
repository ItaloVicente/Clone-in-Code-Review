		if (WorkbenchActivityHelper.isFiltering() && identifier == null) {
			hookListeners();
			invalidateParent();
		} else if (!WorkbenchActivityHelper.isFiltering() && identifier != null) {
			unhookListeners();
			disposeIdentifier();
			invalidateParent();
		}
	}

	public ISelection getSelection() {
		return ((PluginAction) getAction()).getSelection();
	}
