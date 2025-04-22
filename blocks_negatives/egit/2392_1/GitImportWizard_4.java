			final int actionSelection = importWithDirectoriesPage
					.getActionSelection();

			final IProject[] projectsToShare;
			if (actionSelection == GitSelectWizardPage.ACTION_DIALOG_SHARE)
				projectsToShare = shareProjectsPage.getSelectedProjects();
			else
				projectsToShare = null;

			final File repoDir = selectRepoPage.getRepository().getDirectory();

