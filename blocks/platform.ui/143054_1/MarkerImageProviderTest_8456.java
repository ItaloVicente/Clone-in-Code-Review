	public void testStatic() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IMarker marker = null;
		try {
			marker = workspace.getRoot().createMarker(
					"org.eclipse.ui.tests.testmarker"); //$NON-NLS-1$
		} catch (CoreException e) {
			fail(e.getMessage());
		}
