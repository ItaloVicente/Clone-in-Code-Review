public class FeatureSelectionDialog extends AbstractSelectionDialog<AboutInfo> {
	private static final int LIST_WIDTH = 60;

	private static final int LIST_HEIGHT = 10;

	private AboutInfo[] features;

	private ListViewer listViewer;

	private String helpContextId;

	public FeatureSelectionDialog(Shell shell, AboutInfo[] features,
			String primaryFeatureId, String shellTitle, String shellMessage,
			String helpContextId) {

		super(shell);
		if (features == null || features.length == 0) {
			throw new IllegalArgumentException();
		}
		this.features = features;
		this.helpContextId = helpContextId;
		setTitle(shellTitle);
		setMessage(shellMessage);

