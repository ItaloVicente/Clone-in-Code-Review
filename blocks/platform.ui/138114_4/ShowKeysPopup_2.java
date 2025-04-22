package org.eclipse.ui.internal.keys.show;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public class ShowKeysListener implements IExecutionListener, IPropertyChangeListener, IDisposable {

	private IPreferenceStore preferenceStore;
	private IServiceLocator serviceLocator;
	private ShowKeysPopup shortcutPopup;

	public ShowKeysListener(IServiceLocator serviceLocator, IPreferenceStore preferenceStore) {
		this.serviceLocator = serviceLocator;
		this.preferenceStore = preferenceStore;

		if (isEnabled()) {
			ICommandService cmdService = serviceLocator.getService(ICommandService.class);
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

		closePopup();
	}

	@Override
	public void preExecute(String commandId, ExecutionEvent event) {
		Event trigger = (Event) event.getTrigger();
		if (trigger == null) {
			return;
		}
		if (!isEnabled()) {
			return;
		}

		String formattedShortcut = getFormattedShortcut(trigger);
		if (formattedShortcut == null) {
			return;
		}

		try {
			ICommandService cmdService = this.serviceLocator.getService(ICommandService.class);
			Command command = cmdService.getCommand(commandId);
			String name = command.getName();
			String description = command.getDescription();
			openPopup(formattedShortcut, name, description);
		} catch (NotDefinedException ignore) {
		}
	}

	private String getFormattedShortcut(Event trigger) {
		int accelerator = SWTKeySupport.convertEventToUnmodifiedAccelerator(trigger);
		KeyStroke keyStroke = SWTKeySupport.convertAcceleratorToKeyStroke(accelerator);
		if (KeyStroke.NO_KEY != keyStroke.getNaturalKey()) {
			return SWTKeySupport.getKeyFormatterForPlatform().format(keyStroke);
		}
		return null;
	}

	private void openPopup(String shortcut, String shortcutText, String shortcutDescription) {
		closePopup();
		int timeToClose = this.preferenceStore.getInt(IPreferenceConstants.SHOW_KEYS_TIME_TO_CLOSE);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		this.shortcutPopup = new ShowKeysPopup(shell, timeToClose);
		this.shortcutPopup.setShortcut(shortcut, shortcutText, shortcutDescription);
		this.shortcutPopup.open();
	}

	private void closePopup() {
		if (this.shortcutPopup != null) {
			this.shortcutPopup.close();
			this.shortcutPopup = null;
		}
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
