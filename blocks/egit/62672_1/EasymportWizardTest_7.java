import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EasymportWizardTest {

	protected static final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	private static volatile boolean welcomePageClosed = false;

	private static boolean initialAutobuild;

	@BeforeClass
	public static void prepareTest() throws CoreException {
		closeWelcomePage();
		initialAutobuild = setAutobuild(false);
	}

	@AfterClass
	public static void restoreState() throws CoreException {
		setAutobuild(initialAutobuild);
	}

	private static boolean setAutobuild(boolean value) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = workspace.getDescription();
		boolean isAutoBuilding = desc.isAutoBuilding();
		if (isAutoBuilding != value) {
			desc.setAutoBuilding(value);
			workspace.setDescription(desc);
		}
		return isAutoBuilding;
	}
