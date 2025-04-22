	public static void openTextEditor(IWorkbenchPage page,
			IFileRevision revision, IProgressMonitor monitor)
			throws CoreException {
		FileRevisionEditorInput fileRevEditorInput = FileRevisionEditorInput
				.createEditorInputFor(revision, monitor);
		openEditor(page, fileRevEditorInput, "org.eclipse.ui.DefaultTextEditor"); //$NON-NLS-1$
	}

