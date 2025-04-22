	LoggerFactory factory;
	Logger logger;

	private IExtensionRegistry registry;

	private CopyOnWriteArrayList<ServiceReference<IModelProcessorContribution>> processorContributions = new CopyOnWriteArrayList<>();

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
	void setModelProcessorContribution(ServiceReference<IModelProcessorContribution> contrib) {
		this.processorContributions.add(contrib);

		if (processModelExecuted) {
			uiSync.asyncExec(() -> {
				IModelProcessorContribution service = bundleContext.getService(contrib);
				runProcessor(service);
			});
		}
	}

	void unsetModelProcessorContribution(ServiceReference<IModelProcessorContribution> contrib) {
		this.processorContributions.remove(contrib);
		IModelProcessorContribution service = bundleContext.getService(contrib);

		if (this.context != null) {
			uiSync.asyncExec(() -> {
				try {
					ContextInjectionFactory.invoke(service, PreDestroy.class, context);
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

