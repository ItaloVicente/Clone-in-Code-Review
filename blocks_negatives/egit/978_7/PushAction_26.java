
	@Override
	public void execute(IAction action) {
		final Repository repository = getRepository(true);
		if (repository == null)
			return;

		final PushWizard pushWizard;
		try {
			pushWizard = new PushWizard(repository);
		} catch (URISyntaxException x) {
			ErrorDialog.openError(getShell(), UIText.PushAction_wrongURITitle,
					UIText.PushAction_wrongURIDescription, new Status(
							IStatus.ERROR, Activator.getPluginId(), x
									.getMessage(), x));
			return;
		}
		final WizardDialog dialog = new WizardDialog(getShell(), pushWizard);
		dialog.open();
	}

	@Override
	public boolean isEnabled() {
		return getRepository(false) != null;
