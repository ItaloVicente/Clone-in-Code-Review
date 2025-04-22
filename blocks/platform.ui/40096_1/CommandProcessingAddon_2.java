	private IContextProvider provider;

	@Inject
	public CommandProcessingAddon(final IEclipseContext context) {
		this.provider = new IContextProvider() {

			@Override
			public IEclipseContext getContext() {
				return context;
			}
		};
	}

