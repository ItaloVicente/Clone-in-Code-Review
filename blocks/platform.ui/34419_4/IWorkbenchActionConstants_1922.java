package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.ui.views.IViewRegistry;
import org.eclipse.ui.wizards.IWizardRegistry;

public interface IWorkbench extends IAdaptable, IServiceLocator {
	public Display getDisplay();

	public IProgressService getProgressService();

	public void addWorkbenchListener(IWorkbenchListener listener);

	public void removeWorkbenchListener(IWorkbenchListener listener);
	
	public void addWindowListener(IWindowListener listener);

	public void removeWindowListener(IWindowListener listener);

	public boolean close();

	public IWorkbenchWindow getActiveWorkbenchWindow();

	public IEditorRegistry getEditorRegistry();

	public IWorkbenchOperationSupport getOperationSupport();

	public IPerspectiveRegistry getPerspectiveRegistry();

	public PreferenceManager getPreferenceManager();

	@Deprecated
	public IPreferenceStore getPreferenceStore();

	public ISharedImages getSharedImages();

	public int getWorkbenchWindowCount();

	public IWorkbenchWindow[] getWorkbenchWindows();

	public IWorkingSetManager getWorkingSetManager();

	public ILocalWorkingSetManager createLocalWorkingSetManager();

	public IWorkbenchWindow openWorkbenchWindow(String perspectiveId,
			IAdaptable input) throws WorkbenchException;

	public IWorkbenchWindow openWorkbenchWindow(IAdaptable input)
			throws WorkbenchException;

	public boolean restart();

	public boolean restart(boolean useCurrentWorkspace);

	public IWorkbenchPage showPerspective(String perspectiveId,
			IWorkbenchWindow window) throws WorkbenchException;

	public IWorkbenchPage showPerspective(String perspectiveId,
			IWorkbenchWindow window, IAdaptable input)
			throws WorkbenchException;

	public IDecoratorManager getDecoratorManager();

	public boolean saveAllEditors(boolean confirm);

	public IElementFactory getElementFactory(String factoryId);

	IWorkbenchActivitySupport getActivitySupport();

	@Deprecated
	IWorkbenchCommandSupport getCommandSupport();

	@Deprecated
	IWorkbenchContextSupport getContextSupport();

	public IThemeManager getThemeManager();

	public IIntroManager getIntroManager();

	public IWorkbenchHelpSystem getHelpSystem();

	public IWorkbenchBrowserSupport getBrowserSupport();

	public boolean isStarting();
	
	public boolean isClosing();

	public IExtensionTracker getExtensionTracker();

	public IViewRegistry getViewRegistry();

	public IWizardRegistry getNewWizardRegistry();

	public IWizardRegistry getImportWizardRegistry();

	public IWizardRegistry getExportWizardRegistry();
	
	public boolean saveAll(IShellProvider shellProvider,
			IRunnableContext runnableContext, ISaveableFilter filter,
			boolean confirm);

	public IShellProvider getModalDialogShellProvider();
}
