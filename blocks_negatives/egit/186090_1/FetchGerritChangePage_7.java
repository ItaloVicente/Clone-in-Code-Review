	private enum CheckoutMode {
		CREATE_BRANCH, CREATE_TAG, CHECKOUT_FETCH_HEAD, CHERRY_PICK, NOCHECKOUT
	}

	private final @NonNull Repository repository;

	private final IDialogSettings settings;

	private final String lastUriKey;

	private Combo uriCombo;

	private Map<String, ChangeList> changeRefs = new HashMap<>();

	private Text refText;

	private Button createBranch;

	private Button createTag;

	private Button checkoutFetchHead;

	private Button cherryPickFetchHead;

	private Button updateFetchHead;

	private Label placeholder;

	private Label tagTextlabel;

	private Text tagText;

	private Label branchTextlabel;

	private Text branchText;

	private String initialRefText;

	private Composite warningAdditionalRefNotActive;

	private Button activateAdditionalRefs;

	private IInputValidator branchValidator;

	private IInputValidator tagValidator;

	private Button branchEditButton;

	private Button branchCheckoutButton;

	private ExplicitContentProposalAdapter contentProposer;

	private boolean branchTextEdited;

	private boolean refTextEdited;

	private boolean tagTextEdited;

	private boolean fetching;

