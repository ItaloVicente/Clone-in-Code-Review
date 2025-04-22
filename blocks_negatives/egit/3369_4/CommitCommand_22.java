		if (RepositoryMapping.findRepositoryMapping(repository) == null) {
			MessageDialog.openInformation(getShell(event),
					UIText.CommitCommand_committingNotPossible,
					UIText.CommitCommand_noProjectsImported);
			return null;
		}
		IProject[] selectedProjects = ProjectUtil.getProjects(repository);
		new CommitUI(getShell(event), new Repository[] { repository },
				selectedProjects).commit();
