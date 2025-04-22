	@Override
	public void execute(IAction action) {
		final Repository repository = getRepository(true);
		if (repository == null)
			return;

		final FetchWizard fetchWizard;
		try {
			fetchWizard = new FetchWizard(repository);
		} catch (URISyntaxException x) {
			ErrorDialog.openError(getShell(), UIText.FetchAction_wrongURITitle,
					UIText.FetchAction_wrongURIMessage, new Status(
							IStatus.ERROR, Activator.getPluginId(), x
									.getMessage(), x));
			return;
		}
		final WizardDialog dialog = new WizardDialog(getShell(), fetchWizard);
		dialog.open();
	}

	@Override
	public boolean isEnabled() {
		return getRepository(false) != null;
