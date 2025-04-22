			if (testFile.exists() && !testFile.isDirectory()) {
				setErrorMessage(NLS.bind(
						UIText.CreateRepositoryPage_NotADirectoryMessage, dir));
				return;
			}
			boolean hasFiles = testFile.exists() && testFile.list().length > 0;
			if (hasFiles && getBare()) {
				setErrorMessage(NLS.bind(
						UIText.CreateRepositoryPage_NotEmptyMessage, dir));
				return;
			}

			if (hasFiles && !getBare())
				setMessage(NLS.bind(
						UIText.CreateRepositoryPage_NotEmptyMessage, dir),
						IMessageProvider.INFORMATION);
			else
