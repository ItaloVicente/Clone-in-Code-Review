	private static boolean busy(Job[] jobs) {
		for (Job job : jobs) {
			int state = job.getState();
			if (state == Job.RUNNING || state == Job.WAITING) {
				return true;
			}
		}
		return false;
	}

