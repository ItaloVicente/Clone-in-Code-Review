		}
		else if (page instanceof IRepositorySearchResult) {
			currentSearchResult = (IRepositorySearchResult)page;
			return validSource;
		} else if (page == cloneDestination) {
			importWithDirectoriesPage.setRepository(getClonedRepository());
			return importWithDirectoriesPage;
