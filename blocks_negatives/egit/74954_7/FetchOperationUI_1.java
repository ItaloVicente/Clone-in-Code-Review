		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK())
					FetchResultDialog.show(repository, op.getOperationResult(),
							sourceString);
				else
					Activator.handleError(event.getResult().getMessage(), event
							.getResult().getException(), true);
			}
		});
