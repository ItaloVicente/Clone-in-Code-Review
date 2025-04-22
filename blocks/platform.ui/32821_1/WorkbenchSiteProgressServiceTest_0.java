
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
		cursor = ((Control) ((PartSite)site).getModel().getWidget()).getCursor();
		assertNull(cursor); // no jobs, no cursor
