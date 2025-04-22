    }

    @Override
	public void declareImage(String symbolicName, ImageDescriptor descriptor,
            boolean shared) {
        if (symbolicName == null || descriptor == null) {
            throw new IllegalArgumentException();
        }
        WorkbenchImages.declareImage(symbolicName, descriptor, shared);
    }

    @Override
	public IWorkbenchWindowConfigurer getWindowConfigurer(
            IWorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        return ((WorkbenchWindow) window).getWindowConfigurer();
    }

    @Override
