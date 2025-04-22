			IJobManager jobManager = Job.getJobManager();
			if (action.isChecked()) {
				jobManager.suspend();
			} else {
				jobManager.resume();
			}
