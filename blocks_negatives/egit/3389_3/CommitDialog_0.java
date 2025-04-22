			IProject project = file.getProject();
			try {
				ObjectId id = repository.resolve(Constants.HEAD);
				if (id == null
						|| repository.open(id, Constants.OBJ_COMMIT).getType() != Constants.OBJ_COMMIT) {
					return;
				}
			} catch (IOException e1) {
				return;
			}

			GitProvider provider = (GitProvider) RepositoryProvider.getProvider(project);
			GitFileHistoryProvider fileHistoryProvider = (GitFileHistoryProvider) provider.getFileHistoryProvider();

			IFileHistory fileHistory = fileHistoryProvider.getFileHistoryFor(file, IFileHistoryProvider.SINGLE_REVISION, null);

			IFileRevision baseFile = fileHistory.getFileRevisions()[0];
			IFileRevision nextFile = fileHistoryProvider.getWorkspaceFileRevision(file);

			String encoding;
			try {
				encoding = file.getCharset();
			} catch (CoreException e1) {
				encoding = null;
			}

			ITypedElement base = new FileRevisionTypedElement(baseFile, encoding);
			ITypedElement next = new FileRevisionTypedElement(nextFile, encoding);

			GitCompareFileRevisionEditorInput input = new GitCompareFileRevisionEditorInput(next, base, null);
			CompareUI.openCompareDialog(input);
