		
		progressService.schedule(jobWithCursor, 0, true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		System.out.println("after schedule: " + dateFormat.format(new Date()));
		
		while(jobWithCursor.getState() != Job.RUNNING) {
			Thread.sleep(100);
		}
		System.out.println("after running:  " + dateFormat.format(new Date()));
		
		processEvents();
		System.out.println("after process:  " + dateFormat.format(new Date()));
		forceUpdate();		
		System.out.println("after update:   " + dateFormat.format(new Date()));
		processEvents();
		System.out.println("after process2: " + dateFormat.format(new Date()));
		
		Cursor cursor = ((Control) ((PartSite)site).getModel().getWidget()).getCursor();
		System.out.println("after getCursor:" + dateFormat.format(new Date()));
		assertNotNull(cursor);
		
		jobWithCursor.cancel();
		System.out.println("after cancel:   " + dateFormat.format(new Date()));
		processEvents();
		System.out.println("after process3: " + dateFormat.format(new Date()));

		while(jobWithCursor.getState() == Job.RUNNING) {
			Thread.sleep(100);
