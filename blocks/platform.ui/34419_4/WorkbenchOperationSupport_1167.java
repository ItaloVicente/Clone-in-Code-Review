package org.eclipse.ui.internal.operations;


import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class TimeTriggeredProgressMonitorDialog extends ProgressMonitorDialog {

	private int longOperationTime;

	private long triggerTime = -1;

	private boolean dialogOpened = false;
	
	private IProgressMonitor wrapperedMonitor;

	public TimeTriggeredProgressMonitorDialog(Shell parent,
			int longOperationTime) {
		super(parent);
		setOpenOnRun(false);
		this.longOperationTime = longOperationTime;
	}

    public void createWrapperedMonitor() {
        wrapperedMonitor = new IProgressMonitor() {

            IProgressMonitor superMonitor = TimeTriggeredProgressMonitorDialog.super
                    .getProgressMonitor();

            @Override
			public void beginTask(String name, int totalWork) {
                superMonitor.beginTask(name, totalWork);
                checkTicking();
            }

            private void checkTicking() {
            	if (triggerTime < 0) {
					triggerTime = System.currentTimeMillis() + longOperationTime;
				}
    			if (!dialogOpened && System.currentTimeMillis() > triggerTime) {
    				open();
    				dialogOpened = true;
    			}
            }



            @Override
			public void done() {
                superMonitor.done();
                checkTicking();
            }

            @Override
			public void internalWorked(double work) {
                superMonitor.internalWorked(work);
                checkTicking();
            }

            @Override
			public boolean isCanceled() {
                return superMonitor.isCanceled();
            }

            @Override
			public void setCanceled(boolean value) {
                superMonitor.setCanceled(value);

            }

            @Override
			public void setTaskName(String name) {
                superMonitor.setTaskName(name);
                checkTicking();

            }

            @Override
			public void subTask(String name) {
                superMonitor.subTask(name);
                checkTicking();
            }

            @Override
			public void worked(int work) {
                superMonitor.worked(work);
                checkTicking();

            }
        };
    }

    @Override
	public IProgressMonitor getProgressMonitor() {
        if (wrapperedMonitor == null) {
			createWrapperedMonitor();
		}
        return wrapperedMonitor;
    }
    
    @Override
	public void run(final boolean fork, final boolean cancelable,
            final IRunnableWithProgress runnable) throws InvocationTargetException,
            InterruptedException {
    	final InvocationTargetException[] invokes = new InvocationTargetException[1];
        final InterruptedException[] interrupt = new InterruptedException[1];
        Runnable dialogWaitRunnable = new Runnable() {
    		@Override
			public void run() {
    			try {
    				TimeTriggeredProgressMonitorDialog.super.run(fork, cancelable, runnable);
    			} catch (InvocationTargetException e) {
    				invokes[0] = e;
    			} catch (InterruptedException e) {
    				interrupt[0]= e;
    			}
    		}
        };
        final Display display = PlatformUI.getWorkbench().getDisplay();
        if (display == null) {
			return;
		}
        BusyIndicator.showWhile(display, dialogWaitRunnable);
        if (invokes[0] != null) {
            throw invokes[0];
        }
        if (interrupt[0] != null) {
            throw interrupt[0];
        }
     }
}
