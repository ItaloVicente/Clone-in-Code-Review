package org.eclipse.ui.tests.commands;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.commands.ExecutionException;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.commands.NotHandledException;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class Bug74982Test extends UITestCase {

    private boolean selectionEventFired = false;

    public Bug74982Test(final String name) {
        super(name);
    }

    public final void testSelectAllHandlerSendsSelectionEvent()
            throws ExecutionException, NotHandledException {
        final Shell dialog = new Shell(fWorkbench.getActiveWorkbenchWindow()
                .getShell());
        dialog.setLayout(new GridLayout());
        final Text text = new Text(dialog, SWT.SINGLE);
        text.setText("Mooooooooooooooooooooooooooooo");
        text.setLayoutData(new GridData());
        text.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                selectionEventFired = true;
            }
        });

        dialog.pack();
        dialog.open();
        text.setFocus();

        final Display display = fWorkbench.getDisplay();
        while (display.readAndDispatch()) {
        	((Workbench)fWorkbench).getContext().processWaiting();
        }

        final IWorkbenchCommandSupport commandSupport = fWorkbench
                .getCommandSupport();
        final ICommand selectAllCommand = commandSupport.getCommandManager()
                .getCommand("org.eclipse.ui.edit.selectAll");
        selectAllCommand.execute(null);

        assertTrue(
                "The selection event was not fired when the SelectAllHandler was used.",
                selectionEventFired);
    }
}
