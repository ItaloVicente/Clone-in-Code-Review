		assertTrue(jobWithCursor.getState() != Job.RUNNING); 
		
		forceUpdate();
		System.out.println("after update3:  " + dateFormat.format(new Date()));
		processEvents();
		System.out.println("after process7: " + dateFormat.format(new Date()));
		cursor = ((Control) ((PartSite)site).getModel().getWidget()).getCursor();
		
		while(jobWithCursor.getState() != Job.RUNNING) {
			Thread.sleep(100);
