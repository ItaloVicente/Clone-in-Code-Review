package org.eclipse.ui.tests.concurrency;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class ConcurrencyTestSuite extends TestSuite {

    public static final Test suite() {
        return new ConcurrencyTestSuite();
    }

    public ConcurrencyTestSuite() {
    	addTestSuite(ModalContextCrashTest.class);
        addTestSuite(NestedSyncExecDeadlockTest.class);
        addTestSuite(SyncExecWhileUIThreadWaitsForRuleTest.class);
        addTestSuite(SyncExecWhileUIThreadWaitsForLock.class);
        addTestSuite(TestBug105491.class);
        addTestSuite(TestBug108162.class);
        addTestSuite(TestBug138695.class);
        addTestSuite(TestBug98621.class);
        addTestSuite(TransferRuleTest.class);
        addTestSuite(Bug_262032.class);
        addTestSuite(TestBug269121.class);
    }
}
