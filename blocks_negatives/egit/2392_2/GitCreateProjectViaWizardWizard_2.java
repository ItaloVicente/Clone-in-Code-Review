
			final int actionSelection = mySelectionPage.getActionSelection();

			final IProject[] projectsToShare;
			if (actionSelection == GitSelectWizardPage.ACTION_DIALOG_SHARE)
				projectsToShare = mySharePage.getSelectedProjects();
			else
				projectsToShare = null;

