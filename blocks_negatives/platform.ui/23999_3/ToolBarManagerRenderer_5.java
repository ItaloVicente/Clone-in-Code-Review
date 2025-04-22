	private Map<MToolBar, ToolBarManager> modelToManager = new HashMap<MToolBar, ToolBarManager>();
	private Map<ToolBarManager, MToolBar> managerToModel = new HashMap<ToolBarManager, MToolBar>();

	private Map<MToolBarElement, IContributionItem> modelToContribution = new HashMap<MToolBarElement, IContributionItem>();
	private Map<IContributionItem, MToolBarElement> contributionToModel = new HashMap<IContributionItem, MToolBarElement>();

	private Map<MToolBarElement, ToolBarContributionRecord> modelContributionToRecord = new HashMap<MToolBarElement, ToolBarContributionRecord>();

	private Map<MToolBarElement, ArrayList<ToolBarContributionRecord>> sharedElementToRecord = new HashMap<MToolBarElement, ArrayList<ToolBarContributionRecord>>();

