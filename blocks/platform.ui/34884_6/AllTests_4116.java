package org.eclipse.ui.internal.monitoring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EventLoopMonitorThreadTests.class,
	FilterHandlerTests.class,
	DefaultLoggerTests.class})
public class MonitoringTestSuite {
}
