			break;
		}
		case GitSelectWizardPage.GENERAL_WIZARD: {
			final String[] projectName = new String[1];
			final boolean[] defaultLocation = new boolean[0];
			final String[] path = new String[1];
			final File[] repoDir = new File[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					projectName[0] = createGeneralProjectPage.getProjectName();
					defaultLocation[0] = createGeneralProjectPage
							.isDefaultLocation();
					path[1] = importWithDirectoriesPage.getPath();
					repoDir[1] = selectRepoPage.getRepository().getDirectory();
