
package org.eclipse.ui.statushandlers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.WorkbenchErrorHandlerProxy;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.statushandlers.StatusHandlerRegistry;
import org.eclipse.ui.progress.IProgressConstants;

public class StatusManager {
	public static final int NONE = 0;

	public static final int LOG = 0x01;

	public static final int SHOW = 0x02;

	public static final int BLOCK = 0x04;

	private static StatusManager MANAGER;

	private AbstractStatusHandler statusHandler;

	private List loggedStatuses = new ArrayList();

	private ListenerList listeners = new ListenerList();

	public static StatusManager getManager() {
		if (MANAGER == null) {
			MANAGER = new StatusManager();
		}
		return MANAGER;
	}

	private StatusManager() {
		Platform.addLogListener(new StatusManagerLogListener());
	}

	private AbstractStatusHandler getStatusHandler(){
		if(statusHandler == null && StatusHandlerRegistry.getDefault()
					.getDefaultHandlerDescriptor() != null){
			try {
				statusHandler = StatusHandlerRegistry.getDefault()
						.getDefaultHandlerDescriptor().getStatusHandler();
			} catch (CoreException ex) {
				logError("Errors during the default handler creating", ex); //$NON-NLS-1$
			}
		}
		if(statusHandler == null){
			statusHandler = new WorkbenchErrorHandlerProxy();
		}
		return statusHandler;
	}
	public void handle(StatusAdapter statusAdapter, int style) {
		try {
			if (statusAdapter == null) {
				logError(
						"Error occurred during status handling",//$NON-NLS-1$
						new NullPointerException("StatusAdapter object is null")); //$NON-NLS-1$
				return;
			}
			if (statusAdapter.getStatus() == null) {
				logError("Error occurred during status handling",//$NON-NLS-1$
						new NullPointerException("Status object is null")); //$NON-NLS-1$
				return;
			}

			if (!PlatformUI.isWorkbenchRunning()) {
				if (style != StatusManager.NONE) {
					logError(statusAdapter.getStatus());
				}
				return;
			}

			getStatusHandler().handle(statusAdapter, style);
			
			if (!getStatusHandler().supportsNotification(
					INotificationTypes.HANDLED)) {
				generateFakeNotification(statusAdapter, style);
			}
		} catch (Throwable ex) {
			logError(statusAdapter.getStatus());
			logError("Error occurred during status handling", ex); //$NON-NLS-1$
		}
	}

	public void handle(StatusAdapter statusAdapter) {
		handle(statusAdapter, StatusManager.LOG);
	}

	public void handle(IStatus status, int style) {
		StatusAdapter statusAdapter = new StatusAdapter(status);
		handle(statusAdapter, style);
	}

	public void handle(IStatus status) {
		handle(status, StatusManager.LOG);
	}

	public void handle(CoreException coreException,String pluginId) {
		IStatus exceptionStatus = coreException.getStatus();
		handle(new Status(exceptionStatus.getSeverity(), pluginId,
				coreException
				.getLocalizedMessage(), coreException));
	}

	public void addLoggedStatus(IStatus status) {
		loggedStatuses.add(status);
	}

	private void logError(String message, Throwable ex) {
		IStatus status = StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH,
				message, ex);
		addLoggedStatus(status);
		WorkbenchPlugin.log(status);
	}

	private void logError(IStatus status) {
		addLoggedStatus(status);
		WorkbenchPlugin.log(status);
	}

	private class StatusManagerLogListener implements ILogListener {

		@Override
		public void logging(IStatus status, String plugin) {
			if (!loggedStatuses.contains(status)) {
				handle(status, StatusManager.NONE);
			} else {
				loggedStatuses.remove(status);
			}
		}
	}

	public void fireNotification(int type, StatusAdapter[] adapters){
		if(getStatusHandler().supportsNotification(type)){
			doFireNotification(type, adapters);
		}
	}
	
	private void doFireNotification(int type, StatusAdapter[] adapters) {
		Object[] oListeners = listeners.getListeners();
		for (int i = 0; i < oListeners.length; i++) {
			if (oListeners[i] instanceof INotificationListener) {
				((INotificationListener) oListeners[i])
						.statusManagerNotified(type, adapters);
			}
		}
	}
	
	private void generateFakeNotification(StatusAdapter statusAdapter, int style) {
		if (((style & StatusManager.SHOW) == StatusManager.SHOW || (style & StatusManager.BLOCK) == StatusManager.BLOCK)
				&& statusAdapter
						.getProperty(IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY) != Boolean.TRUE) {
			doFireNotification(INotificationTypes.HANDLED,
					new StatusAdapter[] { statusAdapter });
		}
	}
	
	public void addListener(INotificationListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(INotificationListener listener){
		this.listeners.remove(listener);
	}
	
	public interface INotificationListener{
		public void statusManagerNotified(int type, StatusAdapter[] adapters);
	}
	
	public interface INotificationTypes {

		public static final int HANDLED = 0x01;
		
	}
}
