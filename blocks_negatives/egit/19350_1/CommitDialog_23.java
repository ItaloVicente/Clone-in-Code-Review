			IFile file = findFile(commitItem.path);
			if (file == null
					|| RepositoryProvider.getProvider(file.getProject()) == null)
				CompareUtils.compareHeadWithWorkingTree(repository,
						commitItem.path);
			else
				CompareUtils.compareHeadWithWorkspace(repository, file);
