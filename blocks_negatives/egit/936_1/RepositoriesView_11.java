				int state = this.scheduledJob.getState();
				if (state == Job.WAITING || state == Job.RUNNING) {
					this.scheduledJob
							.addJobChangeListener(new JobChangeAdapter() {

								@Override
								public void done(IJobChangeEvent event) {
									showResource(resource);
								}
							});
				} else {
					doSetSelection = true;
