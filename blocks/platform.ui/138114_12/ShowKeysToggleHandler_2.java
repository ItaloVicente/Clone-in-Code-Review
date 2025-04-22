package org.eclipse.ui.internal.keys.show;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public class ShowKeysListener implements IExecutionListener, IPropertyChangeListener, IDisposable {

	private IPreferenceStore preferenceStore;
	private IServiceLocator serviceLocator;
	private ShowKeysUI showKeysUI;

	public ShowKeysListener(IServiceLocator serviceLocator, IPreferenceStore preferenceStore) {
		this.serviceLocator = serviceLocator;
		this.preferenceStore = preferenceStore;
		this.showKeysUI = new ShowKeysUI(serviceLocator, preferenceStore);

		if (isEnabled()) {
			ICommandService cmdService = this.serviceLocator.getService(ICommandService.class);
			cmdService.addExecutionListener(this);
		}
		this.preferenceStore.addPropertyChangeListener(this);
	}

	@Override
	public void dispose() {
		this.preferenceStore.removePropertyChangeListener(this);

		ICommandService cmdService = this.serviceLocator.getService(ICommandService.class);
		if (cmdService != null) {
			cmdService.removeExecutionListener(this);
		}

		showKeysUI.dispose();
	}

	@Override
	public void preExecute(String commandId, ExecutionEvent event) {
		if (!isEnabled() || ShowKeysToggleHandler.COMMAND_ID.equals(commandId)) {
			return;
		}

		Display.getDefault().asyncExec(() -> showKeysUI.open(commandId, (Event) event.getTrigger()));
	}

	private boolean isEnabled() {
		return this.preferenceStore.getBoolean(IPreferenceConstants.SHOW_KEYS_ENABLED);
	}

	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {
	}

	@Override
	public void notHandled(String commandId, NotHandledException exception) {
	}

	@Override
	public void postExecuteFailure(String commandId, ExecutionException exception) {
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (IPreferenceConstants.SHOW_KEYS_ENABLED.equals(property)) {
			ICommandService cmdService = this.serviceLocator.getService(ICommandService.class);
			if (isEnabled()) {
				cmdService.addExecutionListener(this);
			} else {
				cmdService.removeExecutionListener(this);
			}
		}
	}

}
