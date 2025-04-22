
	private static final class BranchMessageDialog extends MessageDialog {
		private final List<RefNode> nodes;

		private BranchMessageDialog(Shell parentShell, List<RefNode> nodes) {
			super(parentShell, UIText.RepositoriesView_ConfirmDeleteTitle,
					null, UIText.RepositoriesView_ConfirmBranchDeletionMessage,
					MessageDialog.QUESTION, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			this.nodes = nodes;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			area.setLayoutData(new GridData(GridData.FILL_BOTH));
			area.setLayout(new FillLayout());

			TableViewer branchesList = new TableViewer(area);
			branchesList.setContentProvider(ArrayContentProvider.getInstance());
			branchesList.setLabelProvider(new GitLabelProvider());
			branchesList.setInput(nodes);
			return area;
		}

	}

