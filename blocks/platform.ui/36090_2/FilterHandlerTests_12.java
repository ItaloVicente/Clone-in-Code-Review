
	@Test
	public void testWildcardFilter() throws Exception {
		FilterHandler filterHandler = new FilterHandler("*.FilterHandlerTests.testW?ld*Filter");
		ThreadMXBean jvmThreadManager = ManagementFactory.getThreadMXBean();
		ThreadInfo threadInfo =
				jvmThreadManager.getThreadInfo(Thread.currentThread().getId(), Integer.MAX_VALUE);
		boolean matched = false;
		for (StackTraceElement element : threadInfo.getStackTrace()) {
			if (filterHandler.matchesFilter(element)) {
				matched = true;
			}
		}
		assertTrue(matched);
	}
