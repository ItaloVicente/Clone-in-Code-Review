		IDocumentProvider provider = getDocumentProvider();
		return provider != null && provider
				.getDocument(getEditorInput()) instanceof DiffDocument;
	}

	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.open();
		IPath path = dialog.getResult();

		if (path == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(path);
		IEditorInput newInput = new FileEditorInput(file);

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			public void execute(final IProgressMonitor monitor)
					throws CoreException {
				IDocumentProvider provider = getDocumentProvider();
				if (provider != null) {
					provider.saveDocument(monitor, newInput,
							provider.getDocument(getEditorInput()), true);
				}
			}
		};

		try {
			getSite().getService(IProgressService.class).busyCursorWhile(op);
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.DiffEditor_SaveError, e.getCause(),
					true);
		}
