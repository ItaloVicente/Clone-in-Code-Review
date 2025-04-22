package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.editors.text.EditorsUI;

public abstract class TextEditorPropertyAction extends Action
		implements IPropertyChangeListener, IWorkbenchAction {


	private ITextViewer viewer;

	private String preferenceKey;

	private IPreferenceStore store;

	public TextEditorPropertyAction(String label, ITextViewer viewer,
			String preferenceKey, boolean initiallyOff) {
		super(label, IAction.AS_CHECK_BOX);
		this.viewer = viewer;
		this.preferenceKey = preferenceKey;
		this.store = EditorsUI.getPreferenceStore();
		if (store != null) {
			store.addPropertyChangeListener(this);
		}
		if (!initiallyOff) {
			synchronizeWithPreference();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(getPreferenceKey())) {
			synchronizeWithPreference();
		}
	}

	protected void synchronizeWithPreference() {
		boolean checked = false;
		if (store != null) {
			checked = store.getBoolean(getPreferenceKey());
		}
		if (checked != isChecked()) {
			setChecked(checked);
			toggleState(checked);
		} else if (checked) {
			toggleState(false);
			toggleState(true);
		}
	}

	protected String getPreferenceKey() {
		return preferenceKey;
	}

	@Override
	public void run() {
		toggleState(isChecked());
		if (store != null) {
			store.setValue(getPreferenceKey(), isChecked());
		}
	}

	@Override
	public void dispose() {
		if (store != null) {
			store.removePropertyChangeListener(this);
		}
	}

	abstract protected void toggleState(boolean checked);

	protected ITextViewer getTextViewer() {
		return viewer;
	}

	protected IPreferenceStore getStore() {
		return store;
	}
}

