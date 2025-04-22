	public static void applyPerspective(IWorkbenchPage activePage) {
		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PropertySheetPerspectiveFactory.class.getName());
		activePage.setPerspective(desc);
		while (Display.getCurrent().readAndDispatch()) {
