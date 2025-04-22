		Policy.setLog(new ILogger() {

			@Override
			public void log(IStatus status) {
				latch.countDown();
				Assert.assertEquals(IStatus.ERROR, status.getSeverity());
				Assert.assertTrue(status.getException() instanceof IllegalArgumentException);
			}
