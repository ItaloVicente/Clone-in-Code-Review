
		case IMPORT_ID:
			BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
				@Override
				public void run() {
					openInstallationImportWizard();
				}
			});
			break;
