package org.eclipse.ui.tests.rcp.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;

public class WorkbenchAdvisorObserver extends RCPTestWorkbenchAdvisor {

    private List operations = new LinkedList();

    private Iterator iterator;

    public final static String INITIALIZE = "initialize"; //$NON-NLS-1$

    public final static String PRE_STARTUP = "preStartup"; //$NON-NLS-1$

    public final static String POST_STARTUP = "postStartup"; //$NON-NLS-1$

    public final static String PRE_WINDOW_OPEN = "preWindowOpen"; //$NON-NLS-1$

    public final static String FILL_ACTION_BARS = "fillActionBars"; //$NON-NLS-1$

    public final static String POST_WINDOW_RESTORE = "postWindowRestore"; //$NON-NLS-1$

    public final static String POST_WINDOW_OPEN = "postWindowOpen"; //$NON-NLS-1$

    public final static String PRE_WINDOW_SHELL_CLOSE = "preWindowShellClose"; //$NON-NLS-1$

    public final static String EVENT_LOOP_EXCEPTION = "eventLoopException"; //$NON-NLS-1$

    public final static String PRE_SHUTDOWN = "preShutdown"; //$NON-NLS-1$

    public final static String POST_SHUTDOWN = "postShutdown"; //$NON-NLS-1$

    public IWorkbenchConfigurer workbenchConfig;

    public WorkbenchAdvisorObserver() {
        super();
    }

    public WorkbenchAdvisorObserver(int idleBeforeExit) {
        super(idleBeforeExit);
    }

    public void resetOperationIterator() {
        iterator = operations.iterator();
    }

    public void assertNextOperation(String expected) {
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(expected, (String) iterator.next());
    }

    public void assertAllOperationsExamined() {
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }

    private void addOperation(String operation) {
        operations.add(operation);
    }

    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);
        workbenchConfig = configurer;
        addOperation(INITIALIZE);
    }

    public void preStartup() {
        super.preStartup();
        addOperation(PRE_STARTUP);
    }

    public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
        super.preWindowOpen(configurer);
        addOperation(PRE_WINDOW_OPEN);
    }

    public void fillActionBars(IWorkbenchWindow window,
            IActionBarConfigurer configurer, int flags) {
        super.fillActionBars(window, configurer, flags);
        addOperation(FILL_ACTION_BARS);
    }

    public void postWindowRestore(IWorkbenchWindowConfigurer configurer)
            throws WorkbenchException {
        super.postWindowRestore(configurer);
        addOperation(POST_WINDOW_RESTORE);
    }

    public void postWindowOpen(IWorkbenchWindowConfigurer configurer) {
        super.postWindowOpen(configurer);
        addOperation(POST_WINDOW_OPEN);
    }

    public void postStartup() {
        super.postStartup();
        addOperation(POST_STARTUP);
    }

    public boolean preWindowShellClose(IWorkbenchWindowConfigurer configurer) {
        if (!super.preWindowShellClose(configurer))
            return false;
        addOperation(PRE_WINDOW_SHELL_CLOSE);
        return true;
    }

    public boolean preShutdown() {
        boolean result = super.preShutdown();
        addOperation(PRE_SHUTDOWN);
        return result;
    }

    public void postShutdown() {
        super.postShutdown();
        addOperation(POST_SHUTDOWN);
    }

    public void eventLoopException(Throwable exception) {
        super.eventLoopException(exception);
        addOperation(EVENT_LOOP_EXCEPTION);
    }
}
