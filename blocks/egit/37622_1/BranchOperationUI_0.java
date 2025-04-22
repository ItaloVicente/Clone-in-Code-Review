		LaunchConfigJob j = new LaunchConfigJob(projects);
		j.schedule();
		try {
			j.join();
		} catch (@SuppressWarnings("unused") InterruptedException e) {
			return null;
