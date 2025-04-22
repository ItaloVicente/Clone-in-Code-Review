	/**
	 * Creates default options which reflect the original configuration of Git
	 * on Unix systems.
	 *
	 * @return created working tree options
	 */
	public static WorkingTreeOptions createDefaultInstance() {
		return new WorkingTreeOptions(AutoCRLF.FALSE);
	}

	/**
	 * Creates options based on the specified repository configuration.
	 *
	 * @param config
	 *            repository configuration to create options for
	 *
	 * @return created working tree options
	 */
	public static WorkingTreeOptions createConfigurationInstance(Config config) {
		return new WorkingTreeOptions(config.get(CoreConfig.KEY).getAutoCRLF());
	}
