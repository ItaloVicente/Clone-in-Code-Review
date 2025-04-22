			break;
		}
		case GitSelectWizardPage.GENERAL_WIZARD: {
			final String[] projectName = new String[1];
			final boolean[] defaultLocation = new boolean[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					projectName[0] = myCreateGeneralProjectPage
							.getProjectName();
					defaultLocation[0] = myCreateGeneralProjectPage
							.isDefaultLocation();
				}
			});
