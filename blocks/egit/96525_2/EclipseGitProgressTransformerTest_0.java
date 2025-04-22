	@Test
	public void testUnboundedMonitorFastUpdate() throws InterruptedException {
		final String title = "Title";

		classUnderTest.beginTask(title, ProgressMonitor.UNKNOWN);
		Mockito.verify(eclipseMonitor).subTask("Title");

		classUnderTest.update(10);
		classUnderTest.update(0);
		Mockito.verify(eclipseMonitor, Mockito.times(1)).subTask("Title, 10");
		for (int i = 0; i < 10; i++) {
			classUnderTest.update(10);
		}
		Thread.sleep(TimeUnit.SECONDS.toMillis(1));
		classUnderTest.update(20);
		Mockito.verify(eclipseMonitor).subTask("Title, 130");

	}

