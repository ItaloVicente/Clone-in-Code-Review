
package org.eclipse.ui.statushandlers;

import org.eclipse.ui.internal.statushandlers.IStatusDialogConstants;

import java.util.Collection;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ErrorSupportProvider;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.statushandlers.WorkbenchStatusDialogManagerImpl;
import org.eclipse.ui.progress.IProgressConstants;

public class WorkbenchStatusDialogManager {
	
	static final QualifiedName HINT = new QualifiedName(
			IStatusAdapterConstants.PROPERTY_PREFIX, "hint"); //$NON-NLS-1$

	private WorkbenchStatusDialogManagerImpl manager;

	
	public WorkbenchStatusDialogManager(int displayMask, String dialogTitle) {
		manager = new WorkbenchStatusDialogManagerImpl(displayMask, dialogTitle);
	}
	
	@Deprecated
	public WorkbenchStatusDialogManager(Shell parentShell, int displayMask,
			String dialogTitle) {
		this(displayMask, dialogTitle);
	}

	public WorkbenchStatusDialogManager(String dialogTitle) {
		this(IStatus.INFO | IStatus.WARNING | IStatus.ERROR | IStatus.CANCEL,
				dialogTitle);
	}

	@Deprecated
	public WorkbenchStatusDialogManager(Shell parentShell, String dialogTitle) {
		this(dialogTitle);
	}

	public void addStatusAdapter(final StatusAdapter statusAdapter,
			final boolean modal) {
		manager.addStatusAdapter(statusAdapter, modal);
	}

	public void enableDefaultSupportArea(boolean enable) {
		manager.getDialogState().put(
				IStatusDialogConstants.ENABLE_DEFAULT_SUPPORT_AREA,
				Boolean.valueOf(enable));
	}

	public Collection getStatusAdapters() {
		return manager.getStatusAdapters();
	}

	public void setDetailsAreaProvider(AbstractStatusAreaProvider provider) {
		manager.setProperty(IStatusDialogConstants.CUSTOM_DETAILS_PROVIDER,
				provider);
	}

	@Deprecated
	public void setStatusListLabelProvider(ITableLabelProvider labelProvider) {
		manager.setStatusListLabelProvider(labelProvider);
	}

	public void setSupportAreaProvider(AbstractStatusAreaProvider provider) {
		manager.setProperty(IStatusDialogConstants.CUSTOM_SUPPORT_PROVIDER,
				provider);
	}

	public void setMessageDecorator(ILabelDecorator decorator){
		manager.setMessageDecorator(decorator);
	}
	
	public void setProperty(Object key, Object value){
		manager.setProperty(key, value);
	}

	public Object getProperty(Object key){
		return manager.getProperty(key);
	}
	
	
	public void enableErrorDialogCompatibility(){
		manager.enableErrorDialogCompatibility();
	}
}
