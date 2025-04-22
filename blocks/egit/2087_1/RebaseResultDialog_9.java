	private final Repository repo;

	private final Set<String> conflictPaths = new HashSet<String>();

	private Button toggleButton;

	private Button startMergeButton;

	private Button abortRebaseButton;

	private Button doNothingButton;

	public static void show(final RebaseResult result,
			final Repository repository) {
		boolean shouldShow = result.getStatus() == Status.STOPPED
				|| !Activator.getDefault().getPreferenceStore().getBoolean(
						UIPreferences.REBASE_HIDE_CONFIRM);
		if (!shouldShow) {
			Activator.getDefault().getLog().log(
					new org.eclipse.core.runtime.Status(IStatus.INFO, Activator
							.getPluginId(), NLS.bind(
							UIText.RebaseResultDialog_StatusLabel, result
									.getStatus().name())));
			return;
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				new RebaseResultDialog(shell, repository, result).open();
			}
		});
	}

