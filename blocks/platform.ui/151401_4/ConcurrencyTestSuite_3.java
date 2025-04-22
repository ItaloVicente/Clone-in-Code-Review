@RunWith(Suite.class)
@Suite.SuiteClasses({
	ModalContextCrashTest.class,
	NestedSyncExecDeadlockTest.class,
	SyncExecWhileUIThreadWaitsForRuleTest.class,
	SyncExecWhileUIThreadWaitsForLock.class,
	TestBug105491.class,
	TestBug108162.class,
	TestBug138695.class,
	TestBug98621.class,
	TransferRuleTest.class,
	Bug_262032.class,
	TestBug269121.class,
})
public final class ConcurrencyTestSuite {
