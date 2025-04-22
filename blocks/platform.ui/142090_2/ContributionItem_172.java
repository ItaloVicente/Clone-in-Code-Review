	private String id = null;

	private boolean visible = true;

	private IContributionManager parent;

	protected ContributionItem() {
		this(null);
	}

	protected ContributionItem(String id) {
		this.id = id;
	}

	@Override
