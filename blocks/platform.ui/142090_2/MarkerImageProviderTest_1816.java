	public void testDynamic() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IMarker marker = null;
		try {
			marker = workspace.getRoot().createMarker(
					"org.eclipse.ui.tests.testmarker2"); //$NON-NLS-1$
		} catch (CoreException e) {
			fail(e.getMessage());
		}
