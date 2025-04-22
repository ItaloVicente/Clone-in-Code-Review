public class BranchSelectionDialog extends TitleAreaDialog {

	private final Repository repo;

	private TreeViewer branchTree;

	/**
	 * button which finally triggers the action
	 */
	protected Button confirmationBtn;
