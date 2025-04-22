
package org.eclipse.ui.internal;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.commands.ICommandService;

public class CycleViewHandler extends CycleBaseHandler {

	@Override
	protected void addItems(Table table, WorkbenchPage page) {
		IWorkbenchPartReference refs[] = page.getSortedParts();
		boolean includeEditor = true;

		for (int i = 0; i < refs.length; i++) {
			if (refs[i] instanceof IEditorReference) {
				if (includeEditor) {
					IEditorReference activeEditor = (IEditorReference) refs[i];
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(WorkbenchMessages.CyclePartAction_editor);
					item.setImage(activeEditor.getTitleImage());
					item.setData(activeEditor);
					includeEditor = false;
				}
			} else {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(refs[i].getTitle());
				item.setImage(refs[i].getTitleImage());
				item.setData(refs[i]);
			}
		}
	}

	@Override
	protected ParameterizedCommand getBackwardCommand() {
		final ICommandService commandService = window.getWorkbench().getService(ICommandService.class);
		final Command command = commandService.getCommand(IWorkbenchCommandConstants.WINDOW_PREVIOUS_VIEW);
		ParameterizedCommand commandBack = new ParameterizedCommand(command, null);
		return commandBack;
	}

	@Override
	protected ParameterizedCommand getForwardCommand() {
		final ICommandService commandService = window.getWorkbench().getService(ICommandService.class);
		final Command command = commandService.getCommand(IWorkbenchCommandConstants.WINDOW_NEXT_VIEW);
		ParameterizedCommand commandF = new ParameterizedCommand(command, null);
		return commandF;
	}

	@Override
	protected String getTableHeader(IWorkbenchPart activePart) {
		return WorkbenchMessages.CyclePartAction_header;
	}

}
