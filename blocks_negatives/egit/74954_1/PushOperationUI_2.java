		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				PushOperationResult result = op.getOperationResult();
				if (expectedResult == null || !expectedResult.equals(result)) {
					if (event.getResult().isOK())
						PushResultDialog.show(repository, result,
								destinationString, showConfigureButton, false);
					else
						Activator.handleError(event.getResult().getMessage(),
								event.getResult().getException(), true);
				}
			}
		});
