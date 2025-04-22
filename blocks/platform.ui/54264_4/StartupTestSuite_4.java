import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ EModelServiceTest.class, EModelServiceFindTest.class, EModelServicePerspectiveFindTest.class,
		EModelServiceInsertTest.class, EPartServiceTest.class, ESelectionServiceTest.class, EventBrokerTest.class,
		HeadlessContactsDemoTest.class, HeadlessPhotoDemoTest.class, UIEventsTest.class,
})
public class StartupTestSuite {
