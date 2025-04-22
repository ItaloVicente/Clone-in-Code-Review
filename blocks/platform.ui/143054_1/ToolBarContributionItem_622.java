	public static final int SHOW_ALL_ITEMS = -1;

	private MenuManager chevronMenuManager;

	private CoolItem coolItem;

	private int currentHeight = -1;

	private int currentWidth = -1;

	private boolean disposed;

	private int minimumItemsToShow = SHOW_ALL_ITEMS;

	private ToolBarManager toolBarManager;

	private boolean useChevron = true;

	public ToolBarContributionItem() {
		this(new ToolBarManager(), null);
	}

	public ToolBarContributionItem(IToolBarManager toolBarManager) {
		this(toolBarManager, null);
	}

	public ToolBarContributionItem(IToolBarManager toolBarManager, String id) {
		super(id);
		Assert.isTrue(toolBarManager instanceof ToolBarManager);
		this.toolBarManager = (ToolBarManager) toolBarManager;
	}

	private final boolean checkDisposed() {
		if (disposed) {
			if (Policy.TRACE_TOOLBAR) {
				System.out.println("Method invocation on a disposed tool bar contribution item."); //$NON-NLS-1$
				new Exception().printStackTrace(System.out);
			}

			return true;
		}

		return false;
	}

	@Override
