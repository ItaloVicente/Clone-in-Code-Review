
	protected void processPreExecutionHooks(String version,
			String origin) throws CoreException {
		String branchHookId = repository.getConfig()
				.getPrefixKey(getCurrentBranchhName());
		processHooks(branchHookId, "pre", version, origin, //$NON-NLS-1$
				getCurrentBranchhName());
	}

	protected void processPostExecutionHooks(String version,
			String origin) throws CoreException {
		String branchHookId = repository.getConfig()
				.getPrefixKey(getCurrentBranchhName());
		processHooks(branchHookId, "post", version, origin, //$NON-NLS-1$
				getCurrentBranchhName());
	}

	protected void processHooks(String branchHookId,
			String hookInceptionId,
			String version, String origin, String branch) throws CoreException {
		String hookName = hookInceptionId + "-flow-" + branchHookId + "-" //$NON-NLS-1$ //$NON-NLS-2$
				+ getOperationHookId();
		String[] hookArguments = new String[] { version, origin, branch };
		ProcessResult processResult = FS.detect().runHookIfPresent(
				repository.getRepository(), hookName, hookArguments);
		if (processResult.getStatus() != OK) {
			Activator.logInfo(hookName + " hook execution ended with status: " //$NON-NLS-1$
					+ processResult.getStatus().name());
			return;
		}
		if (processResult.getExitCode() != 0) {
			throw new CoreException(
					error("Hook did not complete successfully. Exit code: " //$NON-NLS-1$
							+ processResult.getExitCode())); // TODO
		}
	}

	protected EGitFlowOperation getOperationHookId() {
		return null;
	}

	protected String getCurrentBranchhName() {
		try {
			return repository.getRepository().getBranch();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
