
	private static class ConfirmCherryPickDialog extends MessageDialog {

		private RepositoryCommit[] commits;

		public ConfirmCherryPickDialog(Shell parentShell,
				String message, Repository repository, List<RevCommit> revCommits) {
			super(parentShell, UIText.CherryPickHandler_ConfirmTitle, null,
					message, MessageDialog.CONFIRM, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			setShellStyle(getShellStyle() | SWT.RESIZE);

			List<RepositoryCommit> repoCommits = new ArrayList<RepositoryCommit>();
			for (RevCommit commit : revCommits)
				repoCommits.add(new RepositoryCommit(repository, commit));
			this.commits = repoCommits.toArray(new RepositoryCommit[0]);
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			area.setLayoutData(GridDataFactory.fillDefaults().grab(true, true)
					.create());
			area.setLayout(new FillLayout());

			TreeViewer treeViewer = new TreeViewer(area);
			treeViewer.setContentProvider(new ContentProvider());
			treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
					new WorkbenchLabelProvider()));
			treeViewer.setInput(commits);

			return area;
		}

		private static class ContentProvider extends WorkbenchContentProvider {

			public Object[] getElements(final Object element) {
				return (Object[]) element;
			}

			public Object[] getChildren(Object element) {
				if (element instanceof RepositoryCommit)
					return ((RepositoryCommit) element).getDiffs();
				return super.getChildren(element);
			}
		}
	}
