package org.eclipse.egit.ui.internal.preferences;

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.SWT;

public abstract class DoublePreferencesPreferencePage
		extends FieldEditorPreferencePage {

	private IPreferenceStore secondaryStore;

	public DoublePreferencesPreferencePage() {
		super(SWT.FLAT);
	}

	protected DoublePreferencesPreferencePage(int style) {
		super(style);
	}

	protected DoublePreferencesPreferencePage(String title, int style) {
		super(title, style);
	}

	protected DoublePreferencesPreferencePage(String title,
			ImageDescriptor imageDescriptor, int style) {
		super(title, imageDescriptor, style);
	}

	protected IPreferenceStore doGetSecondaryPreferenceStore() {
		return null;
	}

	public IPreferenceStore getSecondaryPreferenceStore() {
		if (secondaryStore == null) {
			secondaryStore = doGetSecondaryPreferenceStore();
		}
		return secondaryStore;
	}

	@Override
	public boolean performOk() {
		boolean isOk = super.performOk();
		if (isOk) {
			saveSecondaryPreferenceStore();
		}
		return isOk;
	}

	@Override
	public void dispose() {
		super.dispose();
		secondaryStore = null;
	}

	private void saveSecondaryPreferenceStore() {
		IPreferenceStore store = getSecondaryPreferenceStore();
		if (store != null && store.needsSaving()
				&& (store instanceof IPersistentPreferenceStore)) {
			try {
				((IPersistentPreferenceStore) store).save();
			} catch (IOException e) {
				String message = JFaceResources.format(
						"PreferenceDialog.saveErrorMessage", //$NON-NLS-1$
						new Object[] { getTitle(), e.getMessage() });
				Policy.getStatusHandler().show(
						new Status(IStatus.ERROR, Activator.getPluginId(),
								message, e),
						JFaceResources
								.getString("PreferenceDialog.saveErrorTitle")); //$NON-NLS-1$
			}
		}
	}
}
