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

    /**
     * Returns the implementation of IWorkbenchAdapter for the given
     * object.  Returns null if the adapter is not defined or the
     * object is not adaptable.
     */
    protected IWorkbenchAdapter getAdapter(Object o) {
        return TestAdaptableWorkbenchAdapter.getInstance();
    }

    @Override
