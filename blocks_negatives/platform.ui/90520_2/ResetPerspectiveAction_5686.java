/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

/**
 * A dynamic menu item which supports to switch to other Windows.
 */
public class ReopenEditorMenu extends ContributionItem {
    private IWorkbenchWindow window;

    private EditorHistory history;

    private boolean showSeparator;

    private static final int MAX_TEXT_LENGTH = 40;

    private static final int MAX_MNEMONIC_SIZE = 9;

    /**
     * Create a new instance.
     * @param window the window on which the menu is to be created
     * @param id menu's id
     * @param showSeparator whether or not to show a separator
     */
    public ReopenEditorMenu(IWorkbenchWindow window, String id,
            boolean showSeparator) {
        super(id);
        this.window = window;
        this.showSeparator = showSeparator;
        IWorkbench workbench = window.getWorkbench();
		if (workbench instanceof Workbench) {
			history = ((Workbench) workbench).getEditorHistory();
		}
    }

    /**
     * Returns the text for a history item.  This may be truncated to fit
     * within the MAX_TEXT_LENGTH.
     */
    private String calcText(int index, EditorHistoryItem item) {
		return calcText(index, item.getName(), item.getToolTipText(), Window
				.getDefaultOrientation() == SWT.RIGHT_TO_LEFT);
	}

    /**
     * Return a string suitable for a file MRU list.  This should not be called
     * outside the framework.
     *
     * @param index the index in the MRU list
     * @param name the file name
     * @param toolTip potentially the path
     * @param rtl should it be right-to-left
     * @return a string suitable for an MRU file menu
     */
    public static String calcText(int index, String name, String toolTip, boolean rtl) {
        StringBuffer sb = new StringBuffer();

        int mnemonic = index + 1;
        StringBuffer nm = new StringBuffer();
        nm.append(mnemonic);
        if (mnemonic <= MAX_MNEMONIC_SIZE) {
        	nm.insert(nm.length() - (mnemonic + "").length(), '&'); //$NON-NLS-1$
        }

        String fileName = name;
        String pathName = toolTip;
        if (pathName.equals(fileName)) {
        }
        IPath path = new Path(pathName);
        if (path.segmentCount() > 1
                && path.segment(path.segmentCount() - 1).equals(fileName)) {
            path = path.removeLastSegments(1);
            pathName = path.toString();
        }

        if ((fileName.length() + pathName.length()) <= (MAX_TEXT_LENGTH - 4)) {
            sb.append(fileName);
            if (pathName.length() > 0) {
                sb.append(pathName);
            }
        } else {
            int length = fileName.length();
            if (length > MAX_TEXT_LENGTH) {
                sb.append(fileName.substring(0, MAX_TEXT_LENGTH - 3));
            } else if (length > MAX_TEXT_LENGTH - 7) {
                sb.append(fileName);
            } else {
                sb.append(fileName);
                int segmentCount = path.segmentCount();
                if (segmentCount > 0) {


                    int i = 0;
                    while (i < segmentCount && length < MAX_TEXT_LENGTH) {
                        String segment = path.segment(i);
                        if (length + segment.length() < MAX_TEXT_LENGTH) {
                            sb.append(segment);
                            sb.append(IPath.SEPARATOR);
                            length += segment.length() + 1;
                            i++;
                        } else if (i == 0) {
                            sb.append(segment.substring(0, MAX_TEXT_LENGTH
                                    - length));
                            length = MAX_TEXT_LENGTH;
                            break;
                        } else {
                            break;
                        }
                    }


                    i = segmentCount - 1;
                    while (i > 0 && length < MAX_TEXT_LENGTH) {
                        String segment = path.segment(i);
                        if (length + segment.length() < MAX_TEXT_LENGTH) {
                            sb.append(IPath.SEPARATOR);
                            sb.append(segment);
                            length += segment.length() + 1;
                            i--;
                        } else {
                            break;
                        }
                    }

                }
            }
        }
        final String process;
        if (rtl) {
        } else {
        }
        return TextProcessor.process(process, TextProcessor.getDefaultDelimiters() + "[]");//$NON-NLS-1$
    }

    /**
     * Fills the given menu with
     * menu items for all windows.
     */
    @Override
	public void fill(final Menu menu, int index) {
        if (window.getActivePage() == null
                || window.getActivePage().getPerspective() == null) {
            return;
        }

        int itemsToShow = WorkbenchPlugin.getDefault().getPreferenceStore()
                .getInt(IPreferenceConstants.RECENT_FILES);
		if (itemsToShow == 0 || history == null) {
            return;
        }

        EditorHistoryItem[] historyItems = history.getItems();

        int n = Math.min(itemsToShow, historyItems.length);
        if (n <= 0) {
            return;
        }

        if (showSeparator) {
            new MenuItem(menu, SWT.SEPARATOR, index);
            ++index;
        }

        final int menuIndex[] = new int[] { index };

        for (int i = 0; i < n; i++) {
            final EditorHistoryItem item = historyItems[i];
            final int historyIndex = i;
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() throws Exception {
                    String text = calcText(historyIndex, item);
                    MenuItem mi = new MenuItem(menu, SWT.PUSH, menuIndex[0]);
                    ++menuIndex[0];
                    mi.setText(text);
                    mi.addSelectionListener(widgetSelectedAdapter(e -> open(item)));
                }

                @Override
				public void handleException(Throwable e) {
                    WorkbenchPlugin.log(getClass(), "fill", e); //$NON-NLS-1$
                }
            });
        }
    }

    /**
     * Overridden to always return true and force dynamic menu building.
     */
    @Override
	public boolean isDynamic() {
        return true;
    }

    /**
     * Reopens the editor for the given history item.
     */
    private void open(EditorHistoryItem item) {
        IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            try {
                String itemName = item.getName();
                if (!item.isRestored()) {
                    item.restoreState();
                }
                IEditorInput input = item.getInput();
                IEditorDescriptor desc = item.getDescriptor();
				if (input == null || !input.exists() || desc == null) {
                    String title = WorkbenchMessages.OpenRecent_errorTitle;
                    String msg = NLS.bind(WorkbenchMessages.OpenRecent_unableToOpen,  itemName );
                    MessageDialog.openWarning(window.getShell(), title, msg);
                    history.remove(item);
                } else {
                    page.openEditor(input, desc.getId());
                }
            } catch (PartInitException e2) {
                String title = WorkbenchMessages.OpenRecent_errorTitle;
                MessageDialog.openWarning(window.getShell(), title, e2
                        .getMessage());
                history.remove(item);
            }
        }
    }

}
