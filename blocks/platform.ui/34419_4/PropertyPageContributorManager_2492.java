package org.eclipse.ui.internal.dialogs;

import java.util.Iterator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IContributionService;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class PropertyDialog extends FilteredPreferenceDialog {
	private ISelection selection;

	private static String lastPropertyId = null;

	public static PropertyDialog createDialogOn(Shell shell,
			final String propertyPageId, Object element) {

		PropertyPageManager pageManager = new PropertyPageManager();
		String title = "";//$NON-NLS-1$

		if (element == null) {
			return null;
		}
		PropertyPageContributorManager.getManager().contribute(pageManager,
				element);
		Iterator pages = pageManager.getElements(PreferenceManager.PRE_ORDER)
				.iterator();
		String name = getName(element);
		if (!pages.hasNext()) {
			MessageDialog.openInformation(shell,
					WorkbenchMessages.PropertyDialog_messageTitle, NLS.bind(
							WorkbenchMessages.PropertyDialog_noPropertyMessage,
							name));
			return null;
		}
		title = NLS
				.bind(WorkbenchMessages.PropertyDialog_propertyMessage, name);
		PropertyDialog propertyDialog = new PropertyDialog(shell, pageManager,
				new StructuredSelection(element));

		if (propertyPageId != null) {
			propertyDialog.setSelectedNode(propertyPageId);
		}
		propertyDialog.create();

		propertyDialog.getShell().setText(title);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				propertyDialog.getShell(),
				IWorkbenchHelpContextIds.PROPERTY_DIALOG);

		return propertyDialog;

	}

	private static String getName(Object element) {
		Object[] elements;
		if (element instanceof IStructuredSelection)
			elements = ((IStructuredSelection) element).toArray();
		else
			elements = new Object[] { element };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			element = elements[i];
			if (i > 2) {
				sb.append(" ..."); //$NON-NLS-1$
				break;
			}
			IWorkbenchAdapter adapter = (IWorkbenchAdapter) Util.getAdapter(element,
					IWorkbenchAdapter.class);
			if (adapter != null) {
				if (sb.length() > 0)
					sb.append(", "); //$NON-NLS-1$
				sb.append(adapter.getLabel(element));
			}
		}
		return sb.toString();
	}

	public PropertyDialog(Shell parentShell, PreferenceManager mng,
			ISelection selection) {
		super(parentShell, mng);
		setSelection(selection);
	}

	public ISelection getSelection() {
		return selection;
	}

	public void setSelection(ISelection newSelection) {
		selection = newSelection;
	}

	@Override
	protected String getSelectedNodePreference() {
		return lastPropertyId;
	}

	@Override
	protected void setSelectedNodePreference(String pageId) {
		lastPropertyId = pageId;
	}

	@Override
	protected String getContributionType() {
		return IContributionService.TYPE_PROPERTY;
	}

	
	
}
