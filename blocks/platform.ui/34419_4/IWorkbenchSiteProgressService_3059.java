package org.eclipse.ui.progress;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public interface IProgressService extends IRunnableContext {

    public int getLongOperationTime();

    public void registerIconForFamily(ImageDescriptor icon, Object family);

    public void runInUI(IRunnableContext context,
            IRunnableWithProgress runnable, ISchedulingRule rule)
            throws InvocationTargetException, InterruptedException;

    public Image getIconFor(Job job);

    public void busyCursorWhile(IRunnableWithProgress runnable)
            throws InvocationTargetException, InterruptedException;

    @Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException;
    
    public void showInDialog(Shell shell, Job job);

}
