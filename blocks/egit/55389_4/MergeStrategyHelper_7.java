package org.eclipse.egit.ui.internal.preferences;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MergePreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage {

	private final MergeStrategyHelper helper;

	public MergePreferencePage() {
		helper = new MergeStrategyHelper(true);
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void init(final IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Control c = helper.createContents(parent);
		helper.load();
		return c;
	}

	@Override
	public boolean performOk() {
		helper.store();
		helper.save();
		return true;
	}
}
