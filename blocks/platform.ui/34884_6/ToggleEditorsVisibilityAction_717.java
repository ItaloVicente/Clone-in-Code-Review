package org.eclipse.ui.internal;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

public class SwitchToWindowMenu extends ContributionItem {
    private static final int MAX_TEXT_LENGTH = 40;

    private IWorkbenchWindow workbenchWindow;

    private boolean showSeparator;

    public SwitchToWindowMenu(IWorkbenchWindow window, String id,
            boolean showSeparator) {
        super(id);
        this.workbenchWindow = window;
        this.showSeparator = showSeparator;
    }

    private String calcText(int number, IWorkbenchWindow window) {
        String suffix = window.getShell().getText();
        if (suffix == null) {
			return null;
		}

        StringBuffer sb = new StringBuffer();
        if (number < 10) {
			sb.append('&');
		}
        sb.append(number);
        sb.append(' ');
        if (suffix.length() <= MAX_TEXT_LENGTH) {
            sb.append(suffix);
        } else {
            sb.append(suffix.substring(0, MAX_TEXT_LENGTH));
            sb.append("..."); //$NON-NLS-1$
        }
        return sb.toString();
    }

    @Override
	public void fill(Menu menu, int index) {

        IWorkbench workbench = workbenchWindow.getWorkbench();
        IWorkbenchWindow[] array = workbench.getWorkbenchWindows();
        if (array.length < 2) {
			return;
		}

        if (showSeparator) {
            new MenuItem(menu, SWT.SEPARATOR, index);
            ++index;
        }

        int count = 1;
        for (int i = 0; i < array.length; i++) {
            final IWorkbenchWindow window = array[i];
            if (!window.getShell().isDisposed()) {
                String name = calcText(count, window);
                if (name != null) {
                    MenuItem mi = new MenuItem(menu, SWT.RADIO, index);
                    index++;
                    count++;
                    mi.setText(name);
                    mi.addSelectionListener(new SelectionAdapter() {
                        @Override
						public void widgetSelected(SelectionEvent e) {
                            Shell windowShell = window.getShell();
                            if (windowShell.getMinimized()) {
								windowShell.setMinimized(false);
							}
                            windowShell.setActive();
                            windowShell.moveAbove(null);
                        }
                    });
                    mi.setSelection(window == workbenchWindow);
                }
            }
        }
    }

    @Override
	public boolean isDirty() {
		return true;
    }

    @Override
	public boolean isDynamic() {
        return true;
    }
}
