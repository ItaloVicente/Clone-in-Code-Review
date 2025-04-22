package org.eclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.util.PrefUtil;

public class DynamicHelpAction extends Action implements IWorkbenchAction {
	private IWorkbenchWindow workbenchWindow;

	public DynamicHelpAction() {
		this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	public DynamicHelpAction(IWorkbenchWindow window) {
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setActionDefinitionId(IWorkbenchCommandConstants.HELP_DYNAMIC_HELP);

		String overrideText = PrefUtil.getAPIPreferenceStore().getString(
				IWorkbenchPreferenceConstants.DYNAMIC_HELP_ACTION_TEXT);
		if ("".equals(overrideText)) { //$NON-NLS-1$
			setText(appendAccelerator(WorkbenchMessages.DynamicHelpAction_text));
			setToolTipText(WorkbenchMessages.DynamicHelpAction_toolTip);
		} else {
			setText(appendAccelerator(overrideText));
			setToolTipText(Action.removeMnemonics(overrideText));
		}
		window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.DYNAMIC_HELP_ACTION);
	}

	private String appendAccelerator(String text) {
	
		return text;
	}

	@Override
	public void run() {
		if (workbenchWindow == null) {
			return;
		}
		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				workbenchWindow.getWorkbench().getHelpSystem()
						.displayDynamicHelp();
			}
		});
	}

	@Override
	public void dispose() {
		workbenchWindow = null;
	}

}
