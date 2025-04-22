		case GitSelectWizardPage.IMPORT_WIZARD: {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					System.setProperty(
							ActionFactory.IMPORT.getId() + ".source", //$NON-NLS-1$
							mySelectionPage.getPath());
					ActionFactory.IMPORT.create(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow()).run();
					System.getProperties().remove(
							ActionFactory.IMPORT.getId() + ".source"); //$NON-NLS-1$
				}
			});
			break;
		}
