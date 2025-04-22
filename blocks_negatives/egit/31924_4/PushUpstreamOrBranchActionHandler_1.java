	/**
	 * @param repository
	 * @param config
	 * @param shell
	 */
	public static void pushOrConfigure(final Repository repository,
			RemoteConfig config, Shell shell) {
		if (config != null) {
			PushOperationUI op = new PushOperationUI(repository,
					config.getName(), false);
			op.start();
		} else {
			Ref head = getHeadIfSymbolic(repository);
			if (head != null) {
				PushBranchWizard pushBranchWizard = new PushBranchWizard(
						repository, head);
