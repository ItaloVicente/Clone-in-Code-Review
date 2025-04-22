package org.eclipse.ui.tests.dialogs;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.eclipse.ui.preferences.WorkingCopyManager;

public class PreferenceDialogWrapper extends PreferenceDialog implements IWorkbenchPreferenceContainer{

    public PreferenceDialogWrapper(Shell parentShell, PreferenceManager manager) {
        super(parentShell, manager);
    }

    @Override
	public boolean showPage(IPreferenceNode node) {
        return super.showPage(node);
    }

    @Override
	public IPreferencePage getPage(IPreferenceNode node) {
        if (node == null)
            return null;

        if (node.getPage() == null)
            node.createPage();

        if (node.getPage() == null)
            return null;

        return node.getPage();
    }

	@Override
	public IWorkingCopyManager getWorkingCopyManager() {
		return new WorkingCopyManager();
	}

	@Override
	public boolean openPage(String preferencePageId, Object data) {
		return false;
	}

	@Override
	public void registerUpdateJob(Job job) {
	}
    
  
}
