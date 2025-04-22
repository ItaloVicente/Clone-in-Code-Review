package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.services.IServiceLocator;

public interface IWorkbenchSite extends IAdaptable, IShellProvider,
		IServiceLocator {

	public IWorkbenchPage getPage();

	public ISelectionProvider getSelectionProvider();

	@Override
	public Shell getShell();

	public IWorkbenchWindow getWorkbenchWindow();

	public void setSelectionProvider(ISelectionProvider provider);

}
