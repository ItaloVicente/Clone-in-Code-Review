				return Status.OK_STATUS;
			}

		};
		delayJob.setPriority(Job.LONG);

		try {
			testThread.start();

			long currentTime = System.currentTimeMillis();


			delayJob.schedule(200);
			delayJob.join();

			long finalTime = System.currentTimeMillis();

			Assert.assertTrue("We tried to sleep the UI thread, but it woke up too early. ",
					finalTime - currentTime >= 200);

			Assert.assertTrue("Background thread did not start, so there was no possibility "
					+ "of testing whether its behavior was correct. This is not a test failure. "
					+ "It means we were unable to run the test. ",
					backgroundThreadStarted);

			Assert.assertFalse("A UI job somehow ran to completion while the UI thread was blocked", uiJobFinished);
			Assert.assertFalse("Background job managed to run to completion, even though it joined a UI thread that still hasn't finished",
					backgroundThreadFinished);
			Assert.assertFalse("Background thread was interrupted", backgroundThreadInterrupted);

