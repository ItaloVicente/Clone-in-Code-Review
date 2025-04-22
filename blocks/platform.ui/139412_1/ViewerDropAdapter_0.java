	public static final int LOCATION_BEFORE = 1;

	public static final int LOCATION_AFTER = 2;

	public static final int LOCATION_ON = 3;

	public static final int LOCATION_NONE = 4;

	private Viewer viewer;

	private int currentOperation = DND.DROP_NONE;

	private int lastValidOperation;

	private int overrideOperation = -1;

	private DropTargetEvent currentEvent;

	private Object currentTarget;

	private int currentLocation;

	private boolean feedbackEnabled = true;

	private boolean scrollEnabled = true;

	private boolean expandEnabled = true;

	private boolean selectFeedbackEnabled = true;

	protected ViewerDropAdapter(Viewer viewer) {
		this.viewer = viewer;
	}

