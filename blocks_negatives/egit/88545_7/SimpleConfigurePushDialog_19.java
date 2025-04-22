public class SimpleConfigurePushDialog extends TitleAreaDialog {
	private static final int DRY_RUN = 98;

	private static final int SAVE_ONLY = 97;

	private static final int REVERT = 96;

	private static final String ADVANCED_MODE_PREFERENCE = SimpleConfigurePushDialog.class
			.getName()

	private final Repository repository;

	private RemoteConfig config;

	private final boolean showBranchInfo;

	private Text commonUriText;
