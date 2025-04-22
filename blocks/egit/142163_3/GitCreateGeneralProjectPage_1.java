			File projectFile = new File(myDirectory,
					IProjectDescription.DESCRIPTION_FILE_NAME);
			if (projectFile.exists()) {
				setErrorMessage(NLS.bind(
						UIText.GitCreateGeneralProjectPage_FileExistsInDirMessage,
						IProjectDescription.DESCRIPTION_FILE_NAME,
						myDirectory.getPath()));
