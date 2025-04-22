		case EXPORT_ID:
			BusyIndicator.showWhile(getShell().getDisplay(), () -> {
				String wizard_id = "org.eclipse.equinox.p2.replication.export"; //$NON-NLS-1$
				IWizardDescriptor descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
						.findWizard(wizard_id);
				if (descriptor != null) {
					IWizard wizard;
					try {
						wizard = descriptor.createWizard();
						WizardDialog wd = new WizardDialog(getShell(), wizard);
						wd.setTitle(wizard.getWindowTitle());
						wd.open();
					} catch (CoreException e) {
						e.printStackTrace();
					}

				}

			});
			break;
		case IMPORT_ID:
			BusyIndicator.showWhile(getShell().getDisplay(), () -> {
				String wizard_id = "org.eclipse.equinox.p2.replication.import"; //$NON-NLS-1$
				IWizardDescriptor descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
						.findWizard(wizard_id);
				if (descriptor != null) {
					IWizard wizard;
					try {
						wizard = descriptor.createWizard();
						WizardDialog wd = new WizardDialog(getShell(), wizard);
						wd.setTitle(wizard.getWindowTitle());
						wd.open();
					} catch (CoreException e) {
						e.printStackTrace();
					}

				}

			});
			break;
