	/**
	 * Add a warning about this remote being used by other branches
	 *
	 * @param parent
	 */
	private void addDefaultOriginWarningIfNeeded(Composite parent) {
		if (!showBranchInfo)
			return;
		List<String> otherBranches = new ArrayList<>();
		String currentBranch;
		try {
			currentBranch = repository.getBranch();
		} catch (IOException e) {
			return;
		}
		String currentRemote = config.getName();
		Config repositoryConfig = repository.getConfig();
		Set<String> branches = repositoryConfig
				.getSubsections(ConfigConstants.CONFIG_BRANCH_SECTION);
		for (String branch : branches) {
			if (branch.equals(currentBranch))
				continue;
			String remote = repositoryConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branch,
					ConfigConstants.CONFIG_KEY_REMOTE);
			if ((remote == null && currentRemote
					.equals(Constants.DEFAULT_REMOTE_NAME))
					|| (remote != null && remote.equals(currentRemote)))
				otherBranches.add(branch);
		}
		if (otherBranches.isEmpty())
			return;

		Composite warningAboutOrigin = new Composite(parent, SWT.NONE);
		warningAboutOrigin.setLayout(new GridLayout(2, false));
		Label warningLabel = new Label(warningAboutOrigin, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		Text warningText = new Text(warningAboutOrigin, SWT.READ_ONLY);
		warningText.setText(NLS.bind(
				UIText.SimpleConfigureFetchDialog_ReusedRemoteWarning, config
						.getName(), Integer.valueOf(otherBranches.size())));
		warningText.setToolTipText(otherBranches.toString());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(warningLabel);
