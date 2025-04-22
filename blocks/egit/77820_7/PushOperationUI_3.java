		try {
			createPushOperation();
		} catch (CoreException e) {
			Activator.showErrorStatus(e.getLocalizedMessage(), e.getStatus());
			return;
		}
		if (credentialsProvider != null) {
			op.setCredentialsProvider(credentialsProvider);
		} else {
			op.setCredentialsProvider(new EGitCredentialsProvider());
		}
		Job job = new PushJob(
				NLS.bind(UIText.PushOperationUI_PushJobName, destinationString),
				repo, op, expectedResult, destinationString,
				showConfigureButton);
