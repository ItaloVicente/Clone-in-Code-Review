public class MonitoringTestSuite extends TestSuite {
	public static Test suite() {
		return new MonitoringTestSuite();
	}

	public MonitoringTestSuite() {
		addTestSuite(EventLoopMonitorThreadTests.class);
		addTestSuite(FilterHandlerTests.class);
		addTestSuite(DefaultLoggerTests.class);
	}
