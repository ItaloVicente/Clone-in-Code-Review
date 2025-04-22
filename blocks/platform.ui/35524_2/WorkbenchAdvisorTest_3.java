
	@Ignore
	@Test
	public void testShellClose() {
		WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver() {

			public void eventLoopIdle(Display disp) {
				super.eventLoopIdle(disp);

				Shell[] shells = disp.getShells();
				for (int i = 0; i < shells.length; ++i)
					if (shells[i] != null)
						shells[i].close();
			}
		};

		int code = PlatformUI.createAndRunWorkbench(display, wa);
		assertEquals(PlatformUI.RETURN_OK, code);

		wa.resetOperationIterator();
		wa.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_SHELL_CLOSE);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
		wa.assertAllOperationsExamined();
	}
