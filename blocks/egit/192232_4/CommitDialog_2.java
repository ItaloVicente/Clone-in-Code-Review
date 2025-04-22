package org.eclipse.egit.ui.internal.commit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.components.DropDownMenuAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;

public class PushSettings {

	private static final String PER_REPO_SETTINGS_PREFIX = "repository_push_settings_"; //$NON-NLS-1$

	private static final String SEPARATOR = ";"; //$NON-NLS-1$

	private static final String NONE = "-"; //$NON-NLS-1$


	private final IPreferenceStore preferences;

	private IPropertyChangeListener listener;

	private boolean forceState;

	private boolean dialogState;

	private String key;

	private String[] values;


	private boolean enabled;

	private boolean visible;

	private ToolBar toolbar;

	private PushSettingsAction action;

	public PushSettings() {
		preferences = Activator.getDefault().getPreferenceStore();
		listener = event -> {
			if (key != null && key.equals(event.getProperty())) {
				runInUiThread(() -> load(key));
			}
		};
		preferences.addPropertyChangeListener(listener);
	}

	public PushSettings(Repository repository) {
		this();
		load(repository);
	}

	public void load(Repository repository) {
		if (repository == null) {
			key = null;
		} else {
			key = PER_REPO_SETTINGS_PREFIX
					+ repository.getDirectory().getAbsolutePath();
		}
		load(key);
	}

	private void load(String prefKey) {
		if (prefKey == null) {
			forceState = false;
			dialogState = false;
			setEnabled(false);
		} else {
			String prefs = preferences.getString(prefKey);
			if (StringUtils.isEmptyOrNull(prefs)) {
				values = null;
			} else {
				values = prefs.split(SEPARATOR);
			}
			if (values == null || values.length < 2) {
				values = new String[] { Boolean.FALSE.toString(), NONE };
			}
			forceState = Boolean.parseBoolean(values[0]);
			if (StringUtils.isEmptyOrNull(values[1])
					|| NONE.equals(values[1])) {
				dialogState = preferences.getBoolean(
						UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT);
			} else {
				dialogState = Boolean.parseBoolean(values[1]);
			}
			setEnabled(true);
		}
		updateImage();
	}

	private void runInUiThread(Runnable r) {
		if (Display.getCurrent() != null) {
			r.run();
		} else {
			Display.getDefault().asyncExec(r);
		}
	}

	private void save() {
		if (key != null) {
			preferences.setValue(key, String.join(SEPARATOR, values));
		}
	}

	public void dispose() {
		if (listener != null) {
			preferences.removePropertyChangeListener(listener);
			listener = null;
		}
	}

	public boolean isForce() {
		return forceState;
	}

	public boolean alwaysShowDialog() {
		return dialogState;
	}

	public Control createControl(Composite parent) {
		if (toolbar == null) {
			ToolBarManager settingsManager = new ToolBarManager(
					SWT.FLAT | SWT.HORIZONTAL);
			action = new PushSettingsAction();
			settingsManager.add(action);
			action.setEnabled(isEnabled());
			toolbar = settingsManager.createControl(parent);
			toolbar.setEnabled(isEnabled());
			updateImage();
		}
		return toolbar;
	}

	public Control getControl() {
		return toolbar;
	}

	private void updateImage() {
		if (action != null && toolbar != null && !toolbar.isDisposed()) {
			action.setImageDescriptor(
					isForce() ? UIIcons.SETTINGS_FORCE : UIIcons.SETTINGS);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (toolbar != null && !toolbar.isDisposed()) {
			toolbar.setEnabled(enabled);
		}
		if (action != null) {
			action.setEnabled(enabled);
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (toolbar != null && !toolbar.isDisposed()) {
			toolbar.setVisible(visible);
		}
	}

	private class PushSettingsAction extends DropDownMenuAction {

		public PushSettingsAction() {
			super(UIText.PushSettings_Title);
			setImageDescriptor(UIIcons.SETTINGS);
		}

		@Override
		protected Collection<IContributionItem> getActions() {
			if (!isEnabled()) {
				return Collections.emptyList();
			}
			List<IContributionItem> items = new ArrayList<>(2);
			Action forceAction = new Action(UIText.PushSettings_Force,
					IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					forceState = isChecked();
					values[0] = Boolean.toString(forceState);
					save();
					updateImage();
				}
			};
			forceAction.setChecked(forceState);
			items.add(new ActionContributionItem(forceAction));
			Action showDialogAction = new Action(
					UIText.PushSettings_DialogAlways, IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					dialogState = isChecked();
					values[1] = Boolean.toString(dialogState);
					save();
				}
			};
			showDialogAction.setChecked(dialogState);
			items.add(new ActionContributionItem(showDialogAction));
			return items;
		}
	}
}
