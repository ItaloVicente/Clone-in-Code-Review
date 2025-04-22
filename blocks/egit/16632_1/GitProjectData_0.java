		m.clear();
		try {
			RepositoryProvider.unmap(getProject());
		} catch (TeamException e) {
			Activator.logError(CoreText.GitProjectData_UnmappingGoneResourceFailed, e);
		}
