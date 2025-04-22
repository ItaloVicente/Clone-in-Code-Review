
			if (getSeparateGitDir()) {
				if (dir.equals(workingTree)) {
					setErrorMessage(
							UIText.CreateRepositoryPage_SeparateWorkingTreeEqualToDirectory);
				} else {
					File workingTreeFile = new File(workingTree);
					if (!workingTreeFile.exists()
							|| !workingTreeFile.isDirectory()) {
						setErrorMessage(MessageFormat.format(
								UIText.CreateRepositoryPage_InvalidWorkingTree,
								workingTree));
					}
				}
			}

