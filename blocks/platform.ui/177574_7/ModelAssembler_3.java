	LoggerFactory factory;
	Logger logger;

	private IExtensionRegistry registry;

	private CopyOnWriteArrayList<IModelProcessorContribution> processorContributions = new CopyOnWriteArrayList<>();

	BundleContext bundleContext;

	BundleTracker<List<FragmentWrapperElementMapping>> tracker;

	private boolean processModelExecuted = false;

	@Activate
	void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;

		this.tracker = new BundleTracker<>(bundleContext,
				Bundle.STARTING | Bundle.ACTIVE | Bundle.STOPPING, new ModelFragmentBundleTracker());
	}

	@Deactivate
	void deactivate() {
		if (this.tracker != null) {
			this.tracker.close();
		}
	}

	@Reference
	public void setExtensionRegistry(IExtensionRegistry registry) {
		this.registry = registry;
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	void setModelProcessorContribution(IModelProcessorContribution contrib) {
		this.processorContributions.add(contrib);

		if (processModelExecuted) {
			uiSync.asyncExec(() -> runProcessor(contrib));
		}
	}

	void unsetModelProcessorContribution(IModelProcessorContribution contrib) {
		this.processorContributions.remove(contrib);

		if (this.context != null) {
			uiSync.asyncExec(() -> {
				try {
					ContextInjectionFactory.invoke(contrib, PreDestroy.class, context);
				} catch (Exception e) {
					log(LogLevel.WARN, "Could not run PreDestroy on processor {0}: {1}", contrib.getClass().getName(), //$NON-NLS-1$
							e);
				}
			});
		}
	}

	@PostConstruct
	public void init(MApplication application, IEclipseContext context, UISynchronize sync) {
		this.application = application;
		this.context = context;
		this.uiSync = sync;
	}

