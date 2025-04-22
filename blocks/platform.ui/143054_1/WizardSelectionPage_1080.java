	private IWizardNode selectedNode = null;

	private List<IWizardNode> selectedWizardNodes = new ArrayList<>();

	protected WizardSelectionPage(String pageName) {
		super(pageName);
		setPageComplete(false);
	}

	private void addSelectedNode(IWizardNode node) {
		if (node == null) {
