package org.eclipse.ui.internal.statushandlers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.statushandlers.StatusManager.INotificationTypes;

public class WorkbenchStatusDialogManagerImpl {

	static final QualifiedName HINT = new QualifiedName(
			IStatusAdapterConstants.PROPERTY_PREFIX, "hint"); //$NON-NLS-1$

	private final class StatusDialogDisposeListener implements DisposeListener {

		@Override
		public void widgetDisposed(org.eclipse.swt.events.DisposeEvent e) {
			cleanUp();
		}
	}

	private DisposeListener disposeListener = new StatusDialogDisposeListener();

	private InternalDialog dialog;

	private Map dialogState = new HashMap();

	public boolean shouldAccept(StatusAdapter statusAdapter) {
		IStatus status = statusAdapter.getStatus();
		IStatus[] children = status.getChildren();
		int mask = ((Integer) dialogState.get(IStatusDialogConstants.MASK))
				.intValue();
		boolean handleOKStatuses = ((Boolean) dialogState
				.get(IStatusDialogConstants.HANDLE_OK_STATUSES)).booleanValue();
		if (children == null || children.length == 0) {
			return status.matches(mask) || (handleOKStatuses && status.isOK());
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i].matches(mask)) {
				return true;
			}
		}
		if (handleOKStatuses && status.isOK()) {
			return true;
		}
		return false;
	}

	public WorkbenchStatusDialogManagerImpl(int displayMask, String dialogTitle) {

		Assert
				.isNotNull(Display.getCurrent(),
						"WorkbenchStatusDialogManager must be instantiated in UI thread"); //$NON-NLS-1$

		dialogState = initDialogState(dialogState, displayMask, dialogTitle);
	}

	public Map initDialogState(Map dialogState, int displayMask, String dialogTitle) {
		dialogState.put(IStatusDialogConstants.MASK, new Integer(displayMask));
		dialogState.put(IStatusDialogConstants.TITLE,
				dialogTitle == null ? JFaceResources
						.getString("Problem_Occurred") : //$NON-NLS-1$
						dialogTitle);
		dialogState.put(IStatusDialogConstants.HANDLE_OK_STATUSES,
				Boolean.FALSE);

		dialogState.put(IStatusDialogConstants.SHOW_SUPPORT, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.ENABLE_DEFAULT_SUPPORT_AREA,
				Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.DETAILS_OPENED, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.TRAY_OPENED, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.HIDE_SUPPORT_BUTTON,
				Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.STATUS_ADAPTERS, Collections
				.synchronizedSet(new HashSet()));
		dialogState.put(IStatusDialogConstants.STATUS_MODALS, new HashMap());
		dialogState.put(IStatusDialogConstants.LABEL_PROVIDER, new LabelProviderWrapper(
				dialogState));
		dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.ANIMATION, Boolean.TRUE);
		return dialogState;
	}

	public void addStatusAdapter(final StatusAdapter statusAdapter,
			final boolean modal) {
		if (ErrorDialog.AUTOMATED_MODE == true) {
			return;
		}
		try {
			doAddStatusAdapter(statusAdapter, modal);
		} catch (Exception e) {
			if (!isDialogClosed()) {
				dialog.getShell().dispose();
			}
			cleanUp();
			WorkbenchPlugin.log(statusAdapter.getStatus());
			WorkbenchPlugin.log(e);
			e.printStackTrace();
		}
	}

	private boolean isDialogClosed() {
		return dialog == null || dialog.getShell() == null
				|| dialog.getShell().isDisposed();
	}

	private void cleanUp() {
		dialog = null;
		getErrors().clear();
		getModals().clear();
		dialogState.put(IStatusDialogConstants.DETAILS_OPENED, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.TRAY_OPENED, Boolean.FALSE);
		dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.FALSE);
	}

	private void doAddStatusAdapter(final StatusAdapter statusAdapter,
			final boolean modal) {

		if (!PlatformUI.isWorkbenchRunning()) {
			WorkbenchPlugin.log(statusAdapter.getStatus());
			return;
		}
		
		if (!shouldAccept(statusAdapter)) {
			return;
		}

		if (isDialogClosed()) {

			getErrors().add(statusAdapter);
			getModals().put(statusAdapter, new Boolean(modal));
			if (shouldPrompt(statusAdapter)) {
				StatusManager.getManager().fireNotification(
						INotificationTypes.HANDLED,
						(StatusAdapter[]) getErrors()
								.toArray(new StatusAdapter[] {}));
				
				if (dialog == null) {
					setSelectedStatusAdapter(statusAdapter);
					dialog = new InternalDialog(dialogState, shouldBeModal());
					dialog.create();
					dialog.getShell().addDisposeListener(disposeListener);
					boolean showSupport = ((Boolean) dialogState
							.get(IStatusDialogConstants.SHOW_SUPPORT))
							.booleanValue();
					if (showSupport) {
						dialog.openTray();
						dialog.getShell().setLocation(
								dialog.getInitialLocation(dialog.getShell().getSize()));
					}
					dialog.open();
				}
				dialog.refresh();
				dialog.refreshDialogSize();
			}

		} else {
			StatusManager.getManager().fireNotification(
					INotificationTypes.HANDLED,
					new StatusAdapter[] { statusAdapter });
			if (statusAdapter
					.getProperty(IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY) != null) {
				statusAdapter.setProperty(
						IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
						Boolean.FALSE);
			}
			openStatusDialog(modal, statusAdapter);
		}
	}

	public Collection getStatusAdapters() {
		return Collections.unmodifiableCollection(getErrors());
	}

	private void openStatusDialog(final boolean modal,
			final StatusAdapter statusAdapter) {
		getErrors().add(statusAdapter);
		getModals().put(statusAdapter, new Boolean(modal));
		boolean shouldBeModal = shouldBeModal();
		if (shouldBeModal ^ dialog.isModal()) {
			dialog.getShell().removeDisposeListener(disposeListener);
			dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.TRUE);
			dialog.close();
			dialog = new InternalDialog(dialogState, modal);
			dialog.open();
			dialog.getShell().addDisposeListener(disposeListener);
			dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.FALSE);
		}
		dialog.refresh();
	}

	public void setSelectedStatusAdapter(StatusAdapter statusAdapter) {
		dialogState.put(IStatusDialogConstants.CURRENT_STATUS_ADAPTER,
				statusAdapter);
	}

	@Deprecated
	public void setStatusListLabelProvider(ITableLabelProvider labelProvider) {
		Assert.isLegal(labelProvider != null, "Label Provider cannot be null"); //$NON-NLS-1$
		dialogState.put(IStatusDialogConstants.CUSTOM_LABEL_PROVIDER,
				labelProvider);
	}

	public boolean shouldBeModal() {
		Map modals = (Map) dialogState
				.get(IStatusDialogConstants.STATUS_MODALS);
		for (Iterator it = modals.keySet().iterator(); it.hasNext();) {
			Object o = it.next();
			Object value = modals.get(o);
			if (value instanceof Boolean) {
				Boolean b = (Boolean) value;
				if (b.booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean shouldPrompt(final StatusAdapter statusAdapter) {
		Object noPromptProperty = statusAdapter
				.getProperty(IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY);

		boolean prompt = true;
		if (noPromptProperty instanceof Boolean) {
			prompt = !((Boolean) noPromptProperty).booleanValue();
		}
		return prompt;
	}

	public Shell getShell() {
		if (this.dialog == null)
			return null;
		return this.dialog.getShell();
	}

	public void setMessageDecorator(ILabelDecorator decorator){
		dialogState.put(IStatusDialogConstants.DECORATOR, decorator);
	}


	public void setProperty(Object key, Object value) {
		dialogState.put(key, value);
	}

	public Object getProperty(Object key){
		if(key == IStatusDialogConstants.SHELL){
			return getShell();
		}
		if (key == IStatusDialogConstants.MANAGER_IMPL) {
			return this;
		}
		return dialogState.get(key);
	}

	public void enableErrorDialogCompatibility(){
		setProperty(IStatusDialogConstants.ERRORLOG_LINK, Boolean.FALSE);
		setProperty(IStatusDialogConstants.HANDLE_OK_STATUSES, Boolean.TRUE);
		setProperty(IStatusDialogConstants.SHOW_SUPPORT, Boolean.TRUE);
		setProperty(IStatusDialogConstants.HIDE_SUPPORT_BUTTON, Boolean.TRUE);
	}

	public InternalDialog getDialog() {
		return dialog;
	}

	public void setDialog(InternalDialog dialog) {
		this.dialog = dialog;
	}

	public Map getDialogState() {
		return dialogState;
	}

	private Collection getErrors() {
		return (Collection) dialogState
				.get(IStatusDialogConstants.STATUS_ADAPTERS);
	}

	private Map getModals() {
		return (Map) dialogState
				.get(IStatusDialogConstants.STATUS_MODALS);
	}
}
