		FetchGerritChangeWizard wiz = new FetchGerritChangeWizard(repository,
				clipText);
		NonBlockingWizardDialog dlg = new NonBlockingWizardDialog(
				HandlerUtil.getActiveShellChecked(event), wiz);
		dlg.setHelpAvailable(false);
		dlg.open();
		return null;
