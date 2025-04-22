	private Composite externalComposite;

	private Composite parentRepoComposite;

	private Text workDir;

	private Text relPath;

	private Button browseRepository;

	private Repository selectedRepository;

	private boolean externalMode = false;

	private CheckboxTableViewer projectMoveViewer;

	private final MoveProjectsLabelProvider moveProjectsLabelProvider = new MoveProjectsLabelProvider();

