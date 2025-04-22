	private ToolBarManager unstagedToolBarManager;

	private ToolBarManager stagedToolBarManager;

	private Action flatAction;

	private Action compressedAction;

	private Action treeAction;

	private Action unstagedExpandAllAction;

	private Action unstagedCollapseAllAction;

	private Action stagedExpandAllAction;

	private Action stagedCollapseAllAction;

	private int presentation;

	private Object[] expandedUnstagedElements;

	private Object[] expandedStagedElements;

	public final static int PRESENTATION_COMPRESSED_FOLDERS = 0;

	public final static int PRESENTATION_FLAT = 1;

	public final static int PRESENTATION_TREE = 2;

