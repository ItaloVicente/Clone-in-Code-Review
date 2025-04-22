	@Execute
	public void processModel(MApplication application, IEclipseContext context, UISynchronize sync,
			@Named("initial") boolean initial) {
		this.application = application;
		this.context = context;
		this.uiSync = sync;

