			final AtomicBoolean done = new AtomicBoolean();

			final String jobName = NLS.bind(
					UIText.RepositoriesView_CheckingOutMessage,
					"refs/remotes/origin/stable");

			listener = new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (jobName.equals(event.getJob().getName()))
						done.set(true);
				}

			};

			Job.getJobManager().addJobChangeListener(listener);

