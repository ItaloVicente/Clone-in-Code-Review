
	public static void pushToUpstream(Shell parent,
			@NonNull Repository repository) throws IOException {
		PushOperationUI op = doPushToUpstream(parent, repository);
		if (op != null) {
			op.start();
		}
	}

	private static PushOperationUI doPushToUpstream(Shell parent,
			@NonNull Repository repository) throws IOException {
		String fullBranch = repository.getFullBranch();
		if (ObjectId.isId(fullBranch)) {
			pushBranchDialog(parent, repository);
			return null;
		}
		String shortBranch = Repository.shortenRefName(fullBranch);
		Config config = repository.getConfig();
		RemoteConfig remoteCfg = PushOperation.getRemote(shortBranch, config);
		if (remoteCfg == null) {
			nothingToPush(parent);
			return null;
		}
		List<RefSpec> refSpecs = remoteCfg.getPushRefSpecs();
		if (!refSpecs.isEmpty()) {
			Collection<RemoteRefUpdate> updates = Transport
					.findRemoteRefUpdatesFor(repository, refSpecs,
							remoteCfg.getFetchRefSpecs());
			if (updates.isEmpty()) {
				nothingToPush(parent);
				return null;
			} else if (updates.size() > 1) {
				List<String> allLocalNames = updates.stream()
						.map(RemoteRefUpdate::getSrcRef)
						.collect(Collectors.toList());
				if (!warnMultiple(parent, allLocalNames)) {
					return null;
				}
			}
			return new PushOperationUI(repository, remoteCfg.getName(), false);
		} else {
			PushDefault pushDefault = config.get(PushConfig::new)
					.getPushDefault();
			switch (pushDefault) {
			case CURRENT:
				return new PushOperationUI(repository, remoteCfg.getName(),
						false);
			case MATCHING:
				List<String> allLocalNames = repository.getRefDatabase()
						.getRefsByPrefix(Constants.R_HEADS).stream()
						.map(Ref::getName).collect(Collectors.toList());
				if (allLocalNames.size() > 1) {
					if (!warnMultiple(parent, allLocalNames)) {
						return null;
					}
				}
				return new PushOperationUI(repository, remoteCfg.getName(),
						false);
			case NOTHING:
				nothingToPush(parent);
				return null;
			case SIMPLE:
			case UPSTREAM:
				BranchConfig branchCfg = new BranchConfig(config, shortBranch);
				String upstreamBranch = branchCfg.getMerge();
				if (upstreamBranch == null) {
					pushBranchDialog(parent, repository);
					return null;
				}
				String fetchRemote = branchCfg.getRemote();
				if (fetchRemote == null) {
					fetchRemote = Constants.DEFAULT_REMOTE_NAME;
				}
				boolean isTriangular = !fetchRemote.equals(remoteCfg.getName());
				if (isTriangular) {
					if (PushDefault.UPSTREAM.equals(pushDefault)) {
						pushBranchDialog(parent, repository);
						return null;
					}
					return new PushOperationUI(repository, remoteCfg.getName(),
							false);
				}
				if (PushDefault.SIMPLE.equals(pushDefault)
						&& !upstreamBranch.equals(fullBranch)) {
					pushBranchDialog(parent, repository);
					return null;
				}
				return new PushOperationUI(repository, fullBranch, remoteCfg,
						false);
			default:
				throw new IllegalStateException(
						"Unknown push.default: " + pushDefault); //$NON-NLS-1$
			}
		}
	}

	private static void nothingToPush(Shell shell) {
		MessageDialog.openInformation(shell,
				UIText.SimplePushActionHandler_NothingToPushDialogTitle,
				UIText.SimplePushActionHandler_NothingToPushDialogMessage);
	}

	private static void pushBranchDialog(Shell shell,
			@NonNull Repository repo) throws IOException {
		Wizard wizard = PushMode.UPSTREAM.getWizard(repo, null);
		if (wizard != null) {
			PushWizardDialog dialog = new PushWizardDialog(shell, wizard);
			dialog.open();
		}
	}

	private static boolean warnMultiple(Shell shell, List<String> refNames) {
		PushMultipleDialog dialog = new PushMultipleDialog(shell, refNames);
		return dialog.open() == Window.OK;
	}

	private static class PushMultipleDialog extends MessageDialog {

		private final List<String> names;

		PushMultipleDialog(Shell parent, List<String> names) {
			super(parent, UIText.PushOperationUI_PushMultipleTitle, null,
					UIText.PushOperationUI_PushMultipleMessage,
					MessageDialog.INFORMATION, IDialogConstants.OK_ID,
					UIText.PushOperationUI_PushMultipleOkLabel,
					IDialogConstants.CANCEL_LABEL);
			this.names = names;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			ResourceManager resources = new LocalResourceManager(
					JFaceResources.getResources());
			parent.addDisposeListener(event -> resources.dispose());
			TableViewer table = new TableViewer(parent,
					SWT.READ_ONLY | SWT.V_SCROLL);
			table.setContentProvider(ArrayContentProvider.getInstance());
			table.setLabelProvider(
					LabelProvider.createImageProvider(element -> {
						if (element.toString().startsWith(Constants.R_HEADS)) {
							return UIIcons.getImage(resources, UIIcons.BRANCH);
						}
						return UIIcons.getImage(resources, UIIcons.COMMIT);
					}));
			table.setInput(names);

			GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
			layoutData.heightHint = Math.min(20, names.size() + 1)
			table.getControl().setLayoutData(layoutData);

			return table.getControl();
		}
	}
