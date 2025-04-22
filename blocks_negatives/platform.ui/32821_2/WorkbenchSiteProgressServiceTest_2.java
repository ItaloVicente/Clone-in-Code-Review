		jobWithCursor = new LongJob();
		
		progressService.schedule(jobWithCursor, 2000, true);
		progressService.schedule(jobWithoutCursor, 0, false);
		System.out.println("after schedule2:" + dateFormat.format(new Date()));
		
		while(jobWithoutCursor.getState() != Job.RUNNING) {
			Thread.sleep(100);
		}
		
		processEvents();
		System.out.println("after process6: " + dateFormat.format(new Date()));
