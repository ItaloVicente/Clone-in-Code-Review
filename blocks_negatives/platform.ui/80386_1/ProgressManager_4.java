			/**
			 * Update the listeners for the receiver for the event.
			 *
			 * @param event
			 */
			private void updateFor(IJobChangeEvent event) {
				if (isInfrastructureJob(event.getJob())) {
					return;
				}
				if (jobs.containsKey(event.getJob())) {
					refreshJobInfo(getJobInfo(event.getJob()));
				} else {
					addJobInfo(new JobInfo(event.getJob()));
				}
			}

