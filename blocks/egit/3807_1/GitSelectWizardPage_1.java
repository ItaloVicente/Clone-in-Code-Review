		try {
			getPath();
		} catch (NoWorkTreeException e) {
			setErrorMessage(UIText.GitImportWithDirectoriesPage_PathErrorMessage + e.getMessage());
		}

