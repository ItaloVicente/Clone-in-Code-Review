	private static final class BranchMessageDialog extends AmbiguousBranchDialog {

		public BranchMessageDialog(Shell parentShell, List<RefNode> nodes) {
			super(parentShell, nodes, UIText.MergeHandler_SelectBranchTitle,
					UIText.MergeHandler_SelectBranchMessage);
		}

	}
