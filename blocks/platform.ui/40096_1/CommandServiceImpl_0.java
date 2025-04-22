	private IContextProvider provider;

	@Inject
	public CommandServiceImpl(final IEclipseContext context) {
		this.provider = new IContextProvider() {

			@Override
			public IEclipseContext getContext() {
				return context;
			}
		};
	}

