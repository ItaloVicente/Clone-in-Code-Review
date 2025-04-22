		createPushOperation();
		if (credentialsProvider != null)
			op.setCredentialsProvider(credentialsProvider);
		try {
			op.run(monitor);
			return op.getOperationResult();
		} catch (InvocationTargetException e) {
			throw new CoreException(Activator.createErrorStatus(e.getCause()
					.getMessage(), e.getCause()));
		}
	}


	private void createPushOperation() throws CoreException {
		if (remoteName != null) {
			op = new PushOperation(repository, remoteName, dryRun, timeout);
			return;
		}

