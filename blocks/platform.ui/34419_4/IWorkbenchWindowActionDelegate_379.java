package org.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.services.IServiceLocator;

public interface IWorkbenchWindow extends IPageService, IRunnableContext,
		IServiceLocator, IShellProvider {
    public boolean close();

    @Override
	public IWorkbenchPage getActivePage();

    public IWorkbenchPage[] getPages();

    public IPartService getPartService();

    public ISelectionService getSelectionService();

    @Override
	public Shell getShell();

    public IWorkbench getWorkbench();

    public boolean isApplicationMenu(String menuId);

    public IWorkbenchPage openPage(String perspectiveId, IAdaptable input)
            throws WorkbenchException;

    public IWorkbenchPage openPage(IAdaptable input) throws WorkbenchException;

    @Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException;

    public void setActivePage(IWorkbenchPage page);
    
    public IExtensionTracker getExtensionTracker();
}
