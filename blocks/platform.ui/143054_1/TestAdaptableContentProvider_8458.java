		if (viewer != null) {
			Object obj = viewer.getInput();
			if (obj instanceof IWorkspace) {
				IWorkspace workspace = (IWorkspace) obj;
				workspace.removeResourceChangeListener(this);
			} else if (obj instanceof IContainer) {
				IWorkspace workspace = ((IContainer) obj).getWorkspace();
				workspace.removeResourceChangeListener(this);
			}
		}
	}

	protected IWorkbenchAdapter getAdapter(Object o) {
		return TestAdaptableWorkbenchAdapter.getInstance();
	}

	@Override
