	public ShowViewDialog(Shell shell, MApplication application, MWindow window,
			EModelService modelService, IEclipseContext context) {
		super(shell);
		this.application = application;
		this.window = window;
		this.modelService = modelService;
		this.context = context;
	}

	@Override
