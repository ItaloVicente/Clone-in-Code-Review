		System.out.println("after done:     " + dateFormat.format(new Date()));
		
		processEvents();
		System.out.println("after process4: " + dateFormat.format(new Date()));
		forceUpdate();
		System.out.println("after update2:  " + dateFormat.format(new Date()));
		processEvents();
		System.out.println("after process5: " + dateFormat.format(new Date()));
		cursor = ((Control) ((PartSite)site).getModel().getWidget()).getCursor();
