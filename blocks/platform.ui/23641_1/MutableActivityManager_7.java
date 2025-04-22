	private boolean addingEvaluationListener = false;

	private List<Identifier> deferredIdentifiers = Collections
			.synchronizedList(new LinkedList<Identifier>());

	private Job deferredIdentifierJob = null;

	private final IActivityRegistryListener activityRegistryListener = new IActivityRegistryListener() {
		public void activityRegistryChanged(ActivityRegistryEvent activityRegistryEvent) {
			readRegistry(false);
		}
	};

	private Map<ActivityDefinition, IEvaluationReference> refsByActivityDefinition = new HashMap<ActivityDefinition, IEvaluationReference>();
