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
