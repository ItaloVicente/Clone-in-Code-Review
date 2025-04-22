		IJobManager manager = Job.getJobManager();
		long waitedForStart = 0;
		while (waitedForStart < 5000) {
			if (manager.find(family).length != 0) {
				break;
			} else {
				Thread.sleep(500);
				waitedForStart += 500;
			}
		}
		manager.join(family, null);
