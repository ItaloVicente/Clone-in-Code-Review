	private Map<MMenu, MenuManager> modelToManager = new HashMap<MMenu, MenuManager>();
	private Map<MenuManager, MMenu> managerToModel = new HashMap<MenuManager, MMenu>();

	private Map<MMenuElement, IContributionItem> modelToContribution = new HashMap<MMenuElement, IContributionItem>();
	private Map<IContributionItem, MMenuElement> contributionToModel = new HashMap<IContributionItem, MMenuElement>();

	private Map<MMenuElement, ContributionRecord> modelContributionToRecord = new HashMap<MMenuElement, ContributionRecord>();
	private Map<MMenuElement, ArrayList<ContributionRecord>> sharedElementToRecord = new HashMap<MMenuElement, ArrayList<ContributionRecord>>();

	@Inject
	private Logger logger;

