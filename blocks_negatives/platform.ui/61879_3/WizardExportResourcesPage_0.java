    private ITreeContentProvider getResourceProvider(final int resourceType) {
        return new WorkbenchContentProvider() {
            @Override
			public Object[] getChildren(Object o) {
                if (o instanceof IContainer) {
                    IResource[] members = null;
                    try {
                        members = ((IContainer) o).members();
                    } catch (CoreException e) {
                        return new Object[0];
                    }
