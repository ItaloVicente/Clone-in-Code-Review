	public void testPerspectiveChangeWith33StickyBehavior() {
		IWorkbench workbench = window.getWorkbench();
		IIntroPart part = workbench.getIntroManager().showIntro(window, false);
		assertNotNull(part);
		IWorkbenchPage activePage = window.getActivePage();
		IPerspectiveDescriptor oldDesc = activePage.getPerspective();
		activePage.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						"org.eclipse.ui.tests.api.SessionPerspective"));
