			done.set(false);
			
			final String jobName2 = NLS.bind(
					UIText.RepositoriesView_CheckingOutMessage,
					"refs/heads/master");

			listener = new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (jobName2.equals(event.getJob().getName()))
						done.set(true);
				}
