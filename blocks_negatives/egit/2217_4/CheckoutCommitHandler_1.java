		TableViewer branchesList;

		RefNode selected;

		private BranchMessageDialog(Shell parentShell, List<RefNode> nodes) {
			super(parentShell, UIText.CheckoutHandler_SelectBranchTitle, null,
					UIText.CheckoutHandler_SelectBranchMessage,
					MessageDialog.QUESTION, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			this.nodes = nodes;
