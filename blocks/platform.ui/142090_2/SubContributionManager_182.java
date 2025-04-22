	private IContributionManager parentMgr;

	private Map<IContributionItem, SubContributionItem> mapItemToWrapper = new HashMap<>();

	private boolean visible = false;

	public SubContributionManager(IContributionManager mgr) {
		super();
		parentMgr = mgr;
	}

	@Override
