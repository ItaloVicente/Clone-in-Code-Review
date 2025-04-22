		case EXPORT_ID:
			BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
				@Override
				public void run() {
					openInstallationExportWizard();
				}
			});
			break;
