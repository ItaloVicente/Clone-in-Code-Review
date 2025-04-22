@RunWith(Suite.class)
@Suite.SuiteClasses({
	OperationsAPITest.class,
	WorkbenchOperationHistoryTests.class,
	MultiThreadedOperationsTests.class,
	WorkbenchOperationStressTests.class,
	WorkspaceOperationsTests.class
})
public class OperationsTestSuite {
