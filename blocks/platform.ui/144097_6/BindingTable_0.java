	private ArrayList<Binding> bindings = new ArrayList<>();
	private Map<TriggerSequence, Binding> bindingsByTrigger = new HashMap<>();
	private Map<ParameterizedCommand, ArrayList<Binding>> bindingsByCommand = new HashMap<>();
	private Map<TriggerSequence, ArrayList<Binding>> bindingsByPrefix = new HashMap<>();
	private Map<TriggerSequence, ArrayList<Binding>> conflicts = new HashMap<>();
	private Map<TriggerSequence, ArrayList<Binding>> orderedBindingsByTrigger = new HashMap<>();
