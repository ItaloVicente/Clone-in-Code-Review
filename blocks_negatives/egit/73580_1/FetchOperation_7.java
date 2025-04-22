		FetchCommand command;
		if (rc == null)
			command = new Git(repository).fetch().setRemote(
					uri.toPrivateString()).setRefSpecs(specs);
		else
			command = new Git(repository).fetch().setRemote(rc.getName());
		command.setCredentialsProvider(credentialsProvider).setTimeout(timeout)
				.setDryRun(dryRun).setProgressMonitor(gitMonitor);
		if (tagOpt != null)
			command.setTagOpt(tagOpt);
		try {
			operationResult = command.call();
		} catch (JGitInternalException e) {
			throw new InvocationTargetException(e.getCause() != null ? e
					.getCause() : e);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
