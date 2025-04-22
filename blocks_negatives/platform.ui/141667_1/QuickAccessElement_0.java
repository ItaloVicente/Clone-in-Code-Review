	private QuickAccessProvider provider;
	private QuickAccessMatcher matcher;

	/**
	 * @param provider
	 */
	public QuickAccessElement(QuickAccessProvider provider) {
		super();
		this.provider = provider;
		this.matcher = new QuickAccessMatcher(this);
	}

