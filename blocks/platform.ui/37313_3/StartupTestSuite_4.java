import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EModelServiceTest.class,
	EModelServiceFindTest.class,
	EModelServiceInsertTest.class,
	EPartServiceTest.class,
	ESelectionServiceTest.class,
	EventBrokerTest.class,
	HeadlessContactsDemoTest.class,
	HeadlessPhotoDemoTest.class,
	UIEventsTest.class,
	UIContactsDemoTest.class,
	UIPhotoDemoTest.class })

public class StartupTestSuite {
	
