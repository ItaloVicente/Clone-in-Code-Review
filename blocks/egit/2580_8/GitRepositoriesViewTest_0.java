		assertClickOpens(tree,
				myUtil.getPluginLocalizedValue("RepoViewCommit.label"),
				UIText.CommitDialog_CommitChanges);
		assertClickOpens(tree,
				myUtil.getPluginLocalizedValue("RepoViewImportProjects.label"),
				NLS.bind(UIText.GitCreateProjectViaWizardWizard_WizardTitle,
						repositoryFile));
