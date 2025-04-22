public class CoolBarManager extends ContributionManager implements ICoolBarManager {

	public static final String USER_SEPARATOR = "UserSeparator"; //$NON-NLS-1$

	private ArrayList<IContributionItem> cbItemsCreationOrder = new ArrayList<>();

	private MenuManager contextMenuManager = null;

	private CoolBar coolBar = null;

	private int itemStyle = SWT.NONE;

	public CoolBarManager() {
	}

	public CoolBarManager(CoolBar coolBar) {
		this();
		Assert.isNotNull(coolBar);
		this.coolBar = coolBar;
		itemStyle = coolBar.getStyle();
	}

	public CoolBarManager(int style) {
		itemStyle = style;
	}

	@Override
