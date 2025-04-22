
		try {
			progressService.schedule(jobWithCursor, 0, true);

			while (jobWithCursor.getState() != Job.RUNNING) {
				Thread.sleep(100);
			}

			processEvents();
			forceUpdate();
			processEvents();

			Cursor cursor = ((Control) ((PartSite) site).getModel().getWidget())
			        .getCursor();
			assertNotNull(cursor);
		} finally {
			jobWithCursor.cancel();
			processEvents();

			while (jobWithCursor.getState() == Job.RUNNING) {
				Thread.sleep(100);
			}

			processEvents();
			forceUpdate();
			processEvents();
