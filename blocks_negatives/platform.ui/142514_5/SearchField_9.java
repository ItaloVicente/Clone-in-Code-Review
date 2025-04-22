	Shell shell;
	private Table table;

	private QuickAccessContents quickAccessContents;

	private Map<String, QuickAccessElement> elementMap = Collections.synchronizedMap(new HashMap<>());
	private Map<QuickAccessElement, ArrayList<String>> textMap = Collections.synchronizedMap(new HashMap<>());
	private List<QuickAccessElement> previousPicksList = Collections.synchronizedList(new LinkedList<>());

	private int dialogHeight = -1;
	private int dialogWidth = -1;
	private Control previousFocusControl;
	boolean activated = false;

	private AccessibleAdapter accessibleListener;
