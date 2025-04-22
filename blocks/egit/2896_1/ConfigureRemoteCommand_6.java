		NewRemoteDialog nrd = new NewRemoteDialog(getShell(event), repository);
		if (nrd.open() != Window.OK)
			return null;

		if (nrd.getPushMode())
			SimpleConfigurePushDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
		else
			SimpleConfigureFetchDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
