		try (Git git = new Git(repository)) {
			FetchCommand command;
			if (rc == null)
				command = git.fetch().setRemote(uri.toPrivateString())
						.setRefSpecs(specs);
			else
				command = git.fetch().setRemote(rc.getName());
			command.setCredentialsProvider(credentialsProvider)
					.setTimeout(timeout).setDryRun(dryRun)
					.setProgressMonitor(gitMonitor);
			if (tagOpt != null)
				command.setTagOpt(tagOpt);
			try {
				operationResult = command.call();
			} catch (JGitInternalException e) {
				throw new InvocationTargetException(
						e.getCause() != null ? e.getCause() : e);
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			}
