package org.eclipse.ui.internal.navigator.resources.actions;

import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ExportResourcesAction;
import org.eclipse.ui.actions.ImportResourcesAction;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.navigator.WizardActionGroup;

public class PortingActionProvider extends CommonActionProvider {

	private static final String COMMON_NAVIGATOR_IMPORT_MENU = "common.import.menu"; //$NON-NLS-1$

	private static final String COMMON_NAVIGATOR_EXPORT_MENU = "common.export.menu"; //$NON-NLS-1$	

	private ImportResourcesAction importAction;

	private ExportResourcesAction exportAction;

	private WizardActionGroup importWizardActionGroup;

	private WizardActionGroup exportWizardActionGroup;

	private boolean disposed = false;

	private boolean contribute= false;

	@Override
	public void init(ICommonActionExtensionSite anExtensionSite) {

		Assert.isTrue(!disposed);

		if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {

			IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite
					.getViewSite()).getWorkbenchWindow();
			importAction = new ImportResourcesAction(window);
			exportAction = new ExportResourcesAction(window);
			importWizardActionGroup = new WizardActionGroup(window, PlatformUI
					.getWorkbench().getImportWizardRegistry(),
					WizardActionGroup.TYPE_IMPORT, anExtensionSite.getContentService());
			exportWizardActionGroup = new WizardActionGroup(window, PlatformUI
					.getWorkbench().getExportWizardRegistry(),
					WizardActionGroup.TYPE_EXPORT, anExtensionSite.getContentService());
			contribute = true;
		}
	}

	@Override
	public void dispose() {
		if(!contribute) {
			return;
		}
		importWizardActionGroup.dispose();
		exportWizardActionGroup.dispose();
		importAction = null;
		exportAction = null;
		disposed = true;
	}

	@Override
	public void fillContextMenu(IMenuManager aMenu) {
		if(!contribute) {
			return;
		}

		Assert.isTrue(!disposed);

		ISelection selection = getContext().getSelection();
		if (!(selection instanceof IStructuredSelection) || ((IStructuredSelection) selection).size() > 1) {
			addSimplePortingMenus(aMenu);
		} else {
			addImportMenu(aMenu);
			addExportMenu(aMenu);
		}
	}

	protected ImageDescriptor getImageDescriptor(String relativePath) {
		String iconPath = "icons/full/"; //$NON-NLS-1$ 
		URL url = FileLocator.find(WorkbenchNavigatorPlugin.getDefault().getBundle(), new Path(iconPath + relativePath), Collections.EMPTY_MAP);
		if (url == null) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		return ImageDescriptor.createFromURL(url);
	}

	private void addSimplePortingMenus(IMenuManager aMenu) {
		aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, importAction);
		aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, exportAction);
	}

	private void addImportMenu(IMenuManager aMenu) {

		importWizardActionGroup.setContext(getContext());
		if (importWizardActionGroup.getWizardActionIds().length == 0) {
			aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, importAction);
			return;
		}

		IMenuManager submenu = new MenuManager(
				WorkbenchNavigatorMessages.PortingActionProvider_ImportResourcesMenu_label,
				COMMON_NAVIGATOR_IMPORT_MENU);
		importWizardActionGroup.fillContextMenu(submenu);

		submenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		submenu.add(new Separator());
		submenu.add(importAction);
		aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, submenu);
	}

	private void addExportMenu(IMenuManager aMenu) {

		exportWizardActionGroup.setContext(getContext());
		if (exportWizardActionGroup.getWizardActionIds().length == 0) {
			aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, exportAction);
			return;
		}
		IMenuManager submenu = new MenuManager(
				WorkbenchNavigatorMessages.PortingActionProvider_ExportResourcesMenu_label,
				COMMON_NAVIGATOR_EXPORT_MENU);
		exportWizardActionGroup.fillContextMenu(submenu);

		submenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
		submenu.add(new Separator());
		submenu.add(exportAction);
		aMenu.appendToGroup(ICommonMenuConstants.GROUP_PORT, submenu);
	}

}
