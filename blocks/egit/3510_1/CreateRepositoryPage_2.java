			String name = nameText.getText();
			if (name.length() == 0) {
				setErrorMessage(UIText.CreateRepositoryPage_MissingNameMessage);
				return;
			}

			IStatus status = ResourcesPlugin.getWorkspace().validateName(name, IResource.FOLDER);
			if (!status.isOK()){
				setErrorMessage(status.getMessage());
				return;
			}

			String dir = getDirectory();
