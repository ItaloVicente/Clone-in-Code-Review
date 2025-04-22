	private void assertActiveWindow(IWorkbenchWindow window) {
		int max = 0;
		while (max < 10 && window != PlatformUI.getWorkbench().getActiveWorkbenchWindow()) {
			max++;
			waitForJobs(max * 100, (max + 1) * 100);
		}
		assertEquals(window, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

