		System.out.println("after done2:    " + dateFormat.format(new Date()));
		
		processEvents();
		System.out.println("after process8: " + dateFormat.format(new Date()));
		
		assertTrue(jobWithCursor.getState() == Job.RUNNING && jobWithoutCursor.getState() == Job.RUNNING);

		forceUpdate();		
		System.out.println("after update4:  " + dateFormat.format(new Date()));
		processEvents();
		System.out.println("after process9: " + dateFormat.format(new Date()));
		cursor = ((Control) ((PartSite)site).getModel().getWidget()).getCursor();
		System.out.println("end:            " + dateFormat.format(new Date()));
