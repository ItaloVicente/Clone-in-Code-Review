package org.eclipse.ui.tests.api;

import junit.framework.Assert;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class UIJobTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    public UIJobTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    private volatile boolean uiJobFinished = false;
    private volatile boolean backgroundThreadStarted = false;
    private volatile boolean backgroundThreadInterrupted = false;
    private volatile boolean backgroundThreadFinishedBeforeUIJob = false;
    private volatile boolean backgroundThreadFinished = false;
    private volatile boolean uiJobFinishedBeforeBackgroundThread = false;
    
    public void testJoin() throws Exception {

        uiJobFinished = false;
        backgroundThreadStarted = false;
        backgroundThreadFinished = false;
        backgroundThreadInterrupted = false;
        uiJobFinishedBeforeBackgroundThread = false;

        final UIJob testJob = new UIJob("blah blah blah") {
	        @Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
	            backgroundThreadFinishedBeforeUIJob = backgroundThreadFinished;
	            uiJobFinished = true;
	            
	            return Status.OK_STATUS;
	        }
        };
        
        testJob.setPriority(Job.INTERACTIVE);
        
        Thread testThread = new Thread() {
        @Override
		public void run() {
            testJob.schedule();
            
            backgroundThreadStarted = true;
            
            try {
                testJob.join();
                uiJobFinishedBeforeBackgroundThread = uiJobFinished;
                backgroundThreadFinished = true;
            } catch (InterruptedException e) {
                backgroundThreadInterrupted = true;
            }
        }
        };
        
        Job delayJob = new Job("blah") {
            @Override
			protected IStatus run(IProgressMonitor monitor) {
                
                return Status.OK_STATUS;
            }
            
        };
        delayJob.setPriority(Job.LONG);
        
        try {
	        testThread.start();
	        
	        long currentTime = System.currentTimeMillis();
	                
	        
	        delayJob.schedule(200);
	        delayJob.join();
	        
	        long finalTime = System.currentTimeMillis();
	        
	        Assert.assertTrue("We tried to sleep the UI thread, but it woke up too early. ",
	                finalTime - currentTime >= 200);
	        
	        Assert.assertTrue("Background thread did not start, so there was no possibility "
	                + "of testing whether its behavior was correct. This is not a test failure. "
	                + "It means we were unable to run the test. ",
	                backgroundThreadStarted);
	        
	        Assert.assertFalse("A UI job somehow ran to completion while the UI thread was blocked", uiJobFinished);
	        Assert.assertFalse("Background job managed to run to completion, even though it joined a UI thread that still hasn't finished",
	                backgroundThreadFinished);
	        Assert.assertFalse("Background thread was interrupted", backgroundThreadInterrupted);
	        
	        Display display = fWindow.getShell().getDisplay();
	        
	        while (!(uiJobFinished && backgroundThreadFinished) ) {    
	            if (finalTime - System.currentTimeMillis() > 3000) {
	                break;
	            }
	            if (!display.readAndDispatch()) {
	                display.sleep();
	            }
	        }
	        
	        Assert.assertTrue("Background thread did not finish (possible deadlock)", backgroundThreadFinished);
	        Assert.assertTrue("Test job did not finish (possible deadlock)", uiJobFinished);
	        Assert.assertFalse("Background thread was interrupted ", backgroundThreadInterrupted);
	        Assert.assertFalse("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob", 
	                backgroundThreadFinishedBeforeUIJob);
	        
	        Assert.assertFalse("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob", 
	                backgroundThreadFinishedBeforeUIJob);
	        
	        Assert.assertTrue("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob", 
	                uiJobFinishedBeforeBackgroundThread);
        } finally {
            
        }
        
    }
    

}
