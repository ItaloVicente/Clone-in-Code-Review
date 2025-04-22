
	static void closeMatchingEditors(final IResource[] resourceRoots) {
		if (resourceRoots.length == 0) {
			return;
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				SafeRunner.run(new SafeRunnable(IDEWorkbenchMessages.ErrorOnCloseEditors) {
					@Override
					public void run() throws CoreException {
						IWorkbenchWindow w = getActiveWindow();
						if (w != null) {
							List<IEditorReference> toClose = getMatchingEditors(resourceRoots, w);
							if (toClose.isEmpty()) {
								return;
							}
							closeEditors(toClose, w);
						}
					}
				});
			}
		};
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), runnable);
	}

	private static IWorkbenchWindow getActiveWindow() {
		IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (w == null) {
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
			if (windows.length > 0) {
				w = windows[0];
			}
		}
		return w;
	}

	private static List<IEditorReference> getMatchingEditors(final IResource[] resourceRoots, IWorkbenchWindow w)
			throws CoreException {
		List<IEditorReference> toClose = new ArrayList<IEditorReference>();
		IEditorReference[] editors = getEditors(w);
		for (int i = 0; i < editors.length; i++) {
			IEditorReference ref = editors[i];
			IResource resource = getAdapter(ref);
			if (resource != null && !resource.exists() && belongsTo(resourceRoots, resource)) {
				toClose.add(ref);
			}
		}
		return toClose;
	}

	private static IEditorReference[] getEditors(IWorkbenchWindow w) {
		if (w != null) {
			IWorkbenchPage page = w.getActivePage();
			if (page != null) {
				return page.getEditorReferences();
			}
		}
		return new IEditorReference[0];
	}

	private static IResource getAdapter(IEditorReference ref) throws CoreException {
		IEditorInput input = ref.getEditorInput();
		if (input instanceof FileEditorInput) {
			FileEditorInput fi = (FileEditorInput) input;
			IFile file = fi.getFile();
			if (file != null) {
				return file;
			}
		}
		Object adapter = input.getAdapter(IFile.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = input.getAdapter(IResource.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = Platform.getAdapterManager().getAdapter(input, IFile.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = Platform.getAdapterManager().getAdapter(input, IResource.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		return null;
	}

	private static boolean belongsTo(IResource[] roots, IResource leaf) {
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].contains(leaf)) {
				return true;
			}
		}
		return false;
	}

	private static void closeEditors(List<IEditorReference> toClose, IWorkbenchWindow w) {
		IWorkbenchPage page = w.getActivePage();
		if (page == null) {
			return;
		}
		page.closeEditors(toClose.toArray(new IEditorReference[toClose.size()]), false);
	}
