		try {
			progressService.schedule(jobWithCursor, 2000, true);
			progressService.schedule(jobWithoutCursor, 0, false);

			while (jobWithoutCursor.getState() != Job.RUNNING) {
				Thread.sleep(100);
			}

			processEvents();

			assertTrue(jobWithCursor.getState() != Job.RUNNING);

			forceUpdate();
			processEvents();
			Cursor cursor = ((Control) ((PartSite) site).getModel().getWidget())
			        .getCursor();
			assertNull(cursor); // jobWithoutCursor is scheduled to run first -

			while (jobWithCursor.getState() != Job.RUNNING) {
				Thread.sleep(100);
			}

			processEvents();

			assertTrue(jobWithCursor.getState() == Job.RUNNING
			        && jobWithoutCursor.getState() == Job.RUNNING);

			forceUpdate();
			processEvents();
			cursor = ((Control) ((PartSite) site).getModel().getWidget())
			        .getCursor();
			assertNotNull(cursor); // both running now - cursor should be set
		} finally {
			jobWithCursor.cancel();
			jobWithoutCursor.cancel();
			processEvents();

			while (jobWithCursor.getState() == Job.RUNNING
			        || jobWithoutCursor.getState() == Job.RUNNING) {
				Thread.sleep(100);
			}

			processEvents();
			forceUpdate();
			processEvents();
