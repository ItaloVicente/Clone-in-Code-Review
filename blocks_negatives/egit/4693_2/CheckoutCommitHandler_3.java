	private static final class BranchMessageDialog extends AmbiguousBranchDialog {

		public BranchMessageDialog(Shell parentShell, List<RefNode> nodes) {
			super(parentShell, nodes, UIText.CheckoutHandler_SelectBranchTitle, UIText.CheckoutHandler_SelectBranchMessage);
		}

	}
