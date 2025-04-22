			final boolean[] done = new boolean[] { false };

			final String jobName = NLS.bind(
					UIText.RepositoriesView_CheckingOutMessage,
					"refs/remotes/origin/stable");

			listener = new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (jobName.equals(event.getJob().getName()))
						done[0] = true;
				}

			};

			Job.getJobManager().addJobChangeListener(listener);

