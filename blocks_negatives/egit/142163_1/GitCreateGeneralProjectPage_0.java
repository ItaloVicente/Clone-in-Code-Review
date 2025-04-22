			String[] dotProjectFiles = myDirectory.list((File dir, String name) -> {
					return true;
				return false;
			});
			if (dotProjectFiles != null && dotProjectFiles.length > 0) {
				setErrorMessage(NLS
						.bind(
								UIText.GitCreateGeneralProjectPage_FileExistsInDirMessage,
								".project", myDirectory.getPath())); //$NON-NLS-1$
