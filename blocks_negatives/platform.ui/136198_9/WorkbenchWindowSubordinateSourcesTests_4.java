		service.addEvaluationListener(with, listener, ISources.ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME);
		assertEquals(Boolean.TRUE, listener.val);
		listener.val = null;

		final IPerspectiveRegistry registry = WorkbenchPlugin.getDefault().getPerspectiveRegistry();
		final IPerspectiveDescriptor perspective1 = registry
				.findPerspectiveWithId("org.eclipse.ui.tests.api.ViewPerspective");
		window.getActivePage().setPerspective(perspective1);
		assertEquals(Boolean.FALSE, listener.val);
		listener.val = null;
		window.getActivePage().closePerspective(perspective1, false, false);
		assertEquals(Boolean.TRUE, listener.val);
