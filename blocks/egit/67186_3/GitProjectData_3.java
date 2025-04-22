		try {
			c.setTeamPrivateMember(true);
		} catch (CoreException e) {
			Activator.logError(MessageFormat.format(
					CoreText.GitProjectData_FailedToMarkTeamPrivate,
					c.getFullPath()), e);
		}
