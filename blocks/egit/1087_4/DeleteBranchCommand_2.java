
	private final class BranchMessageDialog extends MessageDialog {
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
			branchesList.setLabelProvider(new BranchLabelProvider());
			branchesList.setInput(nodes);
			return area;
		}

	}

	private final class BranchLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			RefNode refNode = (RefNode) element;
			return refNode.getObject().getName();
		}

		@Override
		public Image getImage(Object element) {
			return RepositoryTreeNodeType.REF.getIcon();
		}
	}

