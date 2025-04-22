	private static final int MAXIMUM_NUMBER_OF_ELEMENTS = 60;
	private static final int MAXIMUM_NUMBER_OF_TEXT_ENTRIES_PER_ELEMENT = 3;
	private static final int MINIMUM_DIALOG_HEIGHT = 50;
	private static final int MINIMUM_DIALOG_WIDTH = 150;

	private MApplication application;
	private MWindow window;

	private Text txtQuickAccess;
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

	@Inject
	private IBindingService bindingService;
	@Inject
	private EPartService partService;

