			String parentDir = directoryText.getText();
			if (parentDir.length() == 0) {
				setErrorMessage(UIText.CreateRepositoryPage_PleaseSelectDirectoryMessage);
				return;
			}
			String name = nameText.getText();
			if (name.length() == 0) {
				setErrorMessage(UIText.CreateRepositoryPage_MissingNameMessage);
				return;
			}
