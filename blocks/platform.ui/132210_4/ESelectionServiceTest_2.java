public class ESelectionServiceTest {
	@Rule
	public UiTestRule rule = new UiTestRule();
	private EModelService ems;
	private IEclipseContext applicationContext;
	private MApplication application;
	private IPresentationEngine engine;

	@Before
	public void setup() {
		ems = rule.modelService;
		applicationContext = rule.getApplicationContext();
		application = rule.application;
		engine = rule.getEngine();
	}
