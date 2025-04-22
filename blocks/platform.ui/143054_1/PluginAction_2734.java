		return pluginId;
	}

	public void disposeDelegate() {
		if (getDelegate() instanceof IActionDelegate2) {
			((IActionDelegate2) getDelegate()).dispose();
		} else if (getDelegate() instanceof IWorkbenchWindowActionDelegate) {
			((IWorkbenchWindowActionDelegate) getDelegate()).dispose();
		}
		delegate = null;
	}

	public void dispose() {
		disposeDelegate();
		selection = null;
	}

	@Override
