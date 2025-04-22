	private ArrayList<Binding> bindings = new ArrayList<Binding>();
	private Map<TriggerSequence, Binding> bindingsByTrigger = new HashMap<TriggerSequence, Binding>();
	private Map<ParameterizedCommand, ArrayList<Binding>> bindingsByCommand = new HashMap<ParameterizedCommand, ArrayList<Binding>>();
	private Map<TriggerSequence, ArrayList<Binding>> bindingsByPrefix = new HashMap<TriggerSequence, ArrayList<Binding>>();
	private Map<TriggerSequence, ArrayList<Binding>> conflicts = new HashMap<TriggerSequence, ArrayList<Binding>>();
	private Map<TriggerSequence, ArrayList<Binding>> orderedBindingsByTrigger = new HashMap<TriggerSequence, ArrayList<Binding>>();
