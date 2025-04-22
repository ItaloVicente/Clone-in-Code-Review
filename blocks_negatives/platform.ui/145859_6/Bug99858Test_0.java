		boolean joined = false;
		while (!joined) {
			try {
				Job.getJobManager().join(IDEWorkbenchMessages.DeleteResourceAction_jobName, null);
				joined = true;
			} catch (InterruptedException ex) {
				chewUpEvents();
			}
		}

		joined = false;
		while (!joined) {
			try {
				Job.getJobManager().join(IDEWorkbenchMessages.DeleteResourceAction_jobName, null);
				joined = true;
			} catch (InterruptedException ex) {
				chewUpEvents();
			}
		}
