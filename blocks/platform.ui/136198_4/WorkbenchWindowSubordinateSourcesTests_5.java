		IEvaluationReference evaluationListener = service.addEvaluationListener(with, listener,
				ISources.ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME);
		try {
			processEvents();
			assertEquals(Boolean.TRUE, listener.val);
			listener.val = null;

			final IPerspectiveRegistry registry = WorkbenchPlugin.getDefault().getPerspectiveRegistry();
			final IPerspectiveDescriptor perspective1 = registry
					.findPerspectiveWithId("org.eclipse.ui.tests.api.ViewPerspective");

			waitOnShell(window.getShell());
			assertWindowIsActive(window);

			window.getActivePage().setPerspective(perspective1);
			processEvents();
			assertEquals(Boolean.FALSE, listener.val);
			listener.val = null;
			window.getActivePage().closePerspective(perspective1, false, false);
			processEvents();
			assertEquals(Boolean.TRUE, listener.val);
		} finally {
			service.removeEvaluationListener(evaluationListener);
		}
