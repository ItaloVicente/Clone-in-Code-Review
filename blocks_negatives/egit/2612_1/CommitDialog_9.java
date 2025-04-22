			GitProvider provider = (GitProvider) RepositoryProvider.getProvider(project);
			GitFileHistoryProvider fileHistoryProvider = (GitFileHistoryProvider) provider.getFileHistoryProvider();

			IFileHistory fileHistory = fileHistoryProvider.getFileHistoryFor(commitItem.file, IFileHistoryProvider.SINGLE_REVISION, null);

			IFileRevision baseFile = fileHistory.getFileRevisions()[0];
			IFileRevision nextFile = fileHistoryProvider.getWorkspaceFileRevision(commitItem.file);
