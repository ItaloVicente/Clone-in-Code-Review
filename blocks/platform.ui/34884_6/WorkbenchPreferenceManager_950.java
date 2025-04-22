package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dnd.SwtUtil;

public class WorkbenchPreferenceDialog extends FilteredPreferenceDialog {
	private static WorkbenchPreferenceDialog instance = null;
	
	private static final String DIALOG_SETTINGS_SECTION = "WorkbenchPreferenceDialogSettings"; //$NON-NLS-1$
	
	private String initialPageId;

	
	public static final WorkbenchPreferenceDialog createDialogOn(Shell shell, final String preferencePageId) {
		final WorkbenchPreferenceDialog dialog;

		if (instance == null) {

			Shell parentShell = shell;
			if (parentShell == null) {
				final IWorkbench workbench = PlatformUI.getWorkbench();
				final IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
				if (workbenchWindow != null) {
					parentShell = workbenchWindow.getShell();
				} else {
					parentShell = null;
				}
			}

			final PreferenceManager preferenceManager = PlatformUI.getWorkbench()
					.getPreferenceManager();
			dialog = new WorkbenchPreferenceDialog(parentShell, preferenceManager);
			if (preferencePageId != null) {
				dialog.setSelectedNode(preferencePageId);
				dialog.setInitialPage(preferencePageId);
			}
			dialog.create();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(
					dialog.getShell(),
					IWorkbenchHelpContextIds.PREFERENCE_DIALOG);

		} else {
			dialog = instance;
			if (preferencePageId != null) {
				dialog.setCurrentPageId(preferencePageId);
				dialog.setInitialPage(preferencePageId);
			}

		}

		return dialog;
	}

	public WorkbenchPreferenceDialog(Shell parentShell, PreferenceManager manager) {
		super(parentShell, manager);
		Assert.isTrue((instance == null),
				"There cannot be two preference dialogs at once in the workbench."); //$NON-NLS-1$
		instance = this;
		
	}


	@Override
	public boolean close() {
		instance = null;
		return super.close();
	}


	@Override
	protected IPreferenceNode findNodeMatching(String nodeId) {
		IPreferenceNode node = super.findNodeMatching(nodeId);
		if (WorkbenchActivityHelper.filterItem(node)) {
			return null;
		}
		return node;
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();
	}
	
	@Override
	protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
        if (section == null) {
            section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
        } 
        return section;
	}
	
	@Override
	protected int getDialogBoundsStrategy() {
		return DIALOG_PERSISTLOCATION;
	}
	
	@Override
	public int open() {
		IPreferencePage selectedPage = getCurrentPage();
		if ((initialPageId != null) && (selectedPage != null)) {
			Shell shell = getShell();
			if ((shell != null) && (!shell.isDisposed())) {
				shell.open(); // make the dialog visible to properly set the focus
				Control control = selectedPage.getControl();
				if (!SwtUtil.isFocusAncestor(control))
					control.setFocus();
			}
		}
		return super.open();
	}
	
	public void setInitialPage(String pageId) {
		initialPageId = pageId;
	}

}
