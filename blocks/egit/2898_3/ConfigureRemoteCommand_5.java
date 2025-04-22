		if (nrd.getPushMode())
			SimpleConfigurePushDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
		else
			SimpleConfigureFetchDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
