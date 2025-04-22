
	private static final String DRAG_HANDLE = "org.eclipse.e4.ui.workbench.swt.DRAG_HANDLE";

	static {
		Bundle bundle = org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator.getDefault().getBundle();
		IPath path = new Path("$ws$/images/dragHandle.png");
		URL url = FileLocator.find(bundle, path, null);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		if (desc != null)
			JFaceResources.getImageRegistry().put(DRAG_HANDLE, desc);
	}

