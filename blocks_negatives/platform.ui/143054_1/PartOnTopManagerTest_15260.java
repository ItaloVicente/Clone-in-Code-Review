	private LogListener listener = new LogListener() {
		@Override
		public void logged(LogEntry entry) {
			if (!logged) {
				logged = entry.getLevel() == LogService.LOG_ERROR;
			}
		}
	};

	@Before
	public void setUp() {
		logged = false;
		appContext = E4Application.createDefaultContext();
		appContext.set(IWorkbench.PRESENTATION_URI_ARG, PartRenderingEngine.engineURI);

		final Display d = Display.getDefault();
		appContext.set(Realm.class, DisplayRealm.getRealm(d));
		appContext.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				d.syncExec(runnable);
			}

			@Override
			public void asyncExec(Runnable runnable) {
				d.asyncExec(runnable);
			}
		});

		LogReaderService logReaderService = appContext.get(LogReaderService.class);
		logReaderService.addLogListener(listener);
		ems = appContext.get(EModelService.class);
	}

	@After
	public void tearDown() {
		LogReaderService logReaderService = appContext.get(LogReaderService.class);
		logReaderService.removeLogListener(listener);
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
	}
