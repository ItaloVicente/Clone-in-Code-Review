package org.eclipse.ui.internal.keys;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.bindings.EBindingService;
import org.eclipse.e4.ui.bindings.internal.KeyAssistDialog;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class GlobalKeyAssistDialog extends KeyAssistDialog {

	private IEclipseContext context;

	private final String keysPageId = "org.eclipse.ui.preferencePages.Keys"; //$NON-NLS-1$

	private boolean isOpen;

	public GlobalKeyAssistDialog(IEclipseContext context, KeyBindingDispatcher associatedKeyboard) {
		super(context, associatedKeyboard);
		this.context = context;
		setInfoText(getInfoText());
	}

	@Override
	public int open() {
		if (isOpen) {
			return openPreferencePage();
		}
		isOpen = true;
		return super.open();
	}

	@Override
	public boolean close() {
		isOpen = false;
		return super.close();
	}

	private String getInfoText() {
		ECommandService commandService = context.getActiveLeaf().get(ECommandService.class);
		Command cmd = commandService.getCommand(IWorkbenchCommandConstants.WINDOW_SHOW_KEY_ASSIST);

		if (cmd != null) {
			EBindingService bindingService = context.getActiveLeaf().get(EBindingService.class);
			TriggerSequence keySeq = bindingService.getBestSequenceFor(new ParameterizedCommand(
					cmd, null));

			if (keySeq != null) {
				return NLS.bind(KeyAssistMessages.openPreferencePage, keySeq.format());
			}
		}

		return ""; //$NON-NLS-1$
	}

	private int openPreferencePage() {

		Shell shell = getShell();
		if (shell.getParent() != null) {
			shell = shell.getParent().getShell();
		}

		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(shell,
				keysPageId, null, getSelectedBinding());
		close();
		return dialog.open();
	}

}
