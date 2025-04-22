	private static class BlameHistoryPageInput extends HistoryPageInput
			implements IAdaptable {

		private final RevCommit commit;

		private BlameHistoryPageInput(Repository repository, RevCommit commit,
				File file) {
			super(repository, new File[] { file });
			this.commit = commit;
		}

		private BlameHistoryPageInput(Repository repository, RevCommit commit,
				IResource file) {
			super(repository, new IResource[] { file });
			this.commit = commit;
		}

		private BlameHistoryPageInput(Repository repository, RevCommit commit) {
			super(repository);
			this.commit = commit;
		}

		public Object getAdapter(Class adapter) {
			if (RevCommit.class == adapter)
				return commit;
			return Platform.getAdapterManager().getAdapter(this, adapter);
		}
	}

	private static class RevisionSelectionHandler implements
			ISelectionChangedListener {

		private IFile resourceFile;

		private File nonResourceFile;

		private RevisionSelectionHandler(Repository repository, String path,
				IStorage storage) {
			if (storage instanceof IFile)
				resourceFile = (IFile) storage;
			else if (!repository.isBare())
				nonResourceFile = new File(repository.getWorkTree(), path);
		}

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection.isEmpty()
					|| !(selection instanceof IStructuredSelection))
				return;
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (!(first instanceof BlameRevision))
				return;

			IHistoryView part = (IHistoryView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findView(IHistoryView.VIEW_ID);
			if (part == null)
				return;

			BlameRevision revision = (BlameRevision) first;
			BlameHistoryPageInput input;
			if (resourceFile != null)
				input = new BlameHistoryPageInput(revision.getRepository(),
						revision.getCommit(), resourceFile);
			else if (nonResourceFile != null)
				input = new BlameHistoryPageInput(revision.getRepository(),
						revision.getCommit(), nonResourceFile);
			else
				input = new BlameHistoryPageInput(revision.getRepository(),
						revision.getCommit());
			part.showHistoryFor(input);
		}

	}

