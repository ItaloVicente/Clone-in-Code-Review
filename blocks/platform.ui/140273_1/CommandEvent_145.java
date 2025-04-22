	private final boolean attributeValuesByNameChanged;

	private final boolean categoryIdChanged;

	private final ICommand command;

	private final boolean definedChanged;

	private final boolean descriptionChanged;

	private final boolean handledChanged;

	private final boolean keySequenceBindingsChanged;

	private final boolean nameChanged;

	private Map previousAttributeValuesByName;

	public CommandEvent(ICommand command, boolean attributeValuesByNameChanged, boolean categoryIdChanged,
			boolean definedChanged, boolean descriptionChanged, boolean handledChanged,
			boolean keySequenceBindingsChanged, boolean nameChanged, Map previousAttributeValuesByName) {
		if (command == null) {
