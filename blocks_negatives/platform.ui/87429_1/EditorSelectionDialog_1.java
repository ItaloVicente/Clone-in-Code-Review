			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					EditorRegistry reg = (EditorRegistry) WorkbenchPlugin.getDefault().getEditorRegistry();
					externalEditors = reg.getSortedEditorsFromOS();
					externalEditors = filterEditors(externalEditors);
				}
