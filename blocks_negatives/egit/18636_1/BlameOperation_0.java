			HistoryPageInput input;
			if (storage instanceof IFile)
				input = new HistoryPageInput(repository,
						new IResource[] { (IResource) storage });
			else if (!repository.isBare())
				input = new HistoryPageInput(repository, new File[] { new File(
						repository.getWorkTree(), path) });
			else
				input = new HistoryPageInput(repository);
