		String pattern = "{0} - {1}"; //$NON-NLS-1$
		String filterHint = null;
		switch (showAllFilter) {
		case SHOWALLREPO:
			filterHint = UIText.GitHistoryPage_AllChangesInRepoHint;
			break;
		case SHOWALLPROJECT:
			filterHint = UIText.GitHistoryPage_AllChangesInProjectHint;
			break;
		case SHOWALLFOLDER:
			filterHint = UIText.GitHistoryPage_AllChangesInFolderHint;
			break;
		case SHOWALLRESOURCE:
			filterHint = UIText.GitHistoryPage_AllChangesOfResourceHint;
			break;
		}
		return NLS.bind(pattern, getName(), filterHint);
