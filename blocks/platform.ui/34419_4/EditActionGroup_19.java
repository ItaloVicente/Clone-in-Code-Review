package org.eclipse.ui.internal.navigator.resources.actions;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.part.ResourceTransfer;


    public static final String ID = PlatformUI.PLUGIN_ID + ".CopyAction"; //$NON-NLS-1$

    private Shell shell;

    private Clipboard clipboard;

    private PasteAction pasteAction;

    public CopyAction(Shell shell, Clipboard clipboard) {
        super(WorkbenchNavigatorMessages.CopyAction_Cop_); 
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(WorkbenchNavigatorMessages.CopyAction_Copy_selected_resource_s_); 
        setId(CopyAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "CopyHelpId"); //$NON-NLS-1$
    }

    public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
        this(shell, clipboard);
        this.pasteAction = pasteAction;
    }

    @Override
	public void run() {
        List<IResource> selectedResources = getSelectedResources();
        IResource[] resources =selectedResources
                .toArray(new IResource[selectedResources.size()]);

        final int length = resources.length;
        int actualLength = 0;
        String[] fileNames = new String[length];
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            IPath location = resources[i].getLocation();
            if (location != null) {
				fileNames[actualLength++] = location.toOSString();
			}
            if (i > 0) {
				buf.append("\n"); //$NON-NLS-1$
			}
            buf.append(resources[i].getName());
        }
        if (actualLength < length) {
            String[] tempFileNames = fileNames;
            fileNames = new String[actualLength];
            for (int i = 0; i < actualLength; i++) {
				fileNames[i] = tempFileNames[i];
			}
        }
        setClipboard(resources, fileNames, buf.toString());

        if (pasteAction != null && pasteAction.getStructuredSelection() != null) {
			pasteAction.selectionChanged(pasteAction.getStructuredSelection());
		}
    }

    private void setClipboard(IResource[] resources, String[] fileNames,
            String names) {
        try {
            if (fileNames.length > 0) {
                clipboard.setContents(new Object[] { resources, fileNames,
                        names },
                        new Transfer[] { ResourceTransfer.getInstance(),
                                FileTransfer.getInstance(),
                                TextTransfer.getInstance() });
            } else {
                clipboard.setContents(new Object[] { resources, names },
                        new Transfer[] { ResourceTransfer.getInstance(),
                                TextTransfer.getInstance() });
            }
        } catch (SWTError e) {
            if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
            if (MessageDialog
                    .openQuestion(
                            shell,
                            "Problem with copy title", // TODO ResourceNavigatorMessages.CopyToClipboardProblemDialog_title,  //$NON-NLS-1$
                            "Problem with copy.")) { //$NON-NLS-1$
				setClipboard(resources, fileNames, names);
			}
        }
    }

    @Override
	protected boolean updateSelection(IStructuredSelection selection) {
        if (!super.updateSelection(selection)) {
			return false;
		}

        if (getSelectedNonResources().size() > 0) {
			return false;
		}

        List<IResource> selectedResources = getSelectedResources();
        if (selectedResources.size() == 0) {
			return false;
		}

        boolean projSelected = selectionIsOfType(IResource.PROJECT);
        boolean fileFoldersSelected = selectionIsOfType(IResource.FILE
                | IResource.FOLDER);
        if (!projSelected && !fileFoldersSelected) {
			return false;
		}

        if (projSelected && fileFoldersSelected) {
			return false;
		}

        IContainer firstParent = selectedResources.get(0).getParent();
        if (firstParent == null) {
			return false;
		}

        for (IResource currentResource : selectedResources) {
        	if (!currentResource.getParent().equals(firstParent)) {
				return false;
			}
            if (currentResource.getLocationURI() == null) {
				return false;
			}
        }

        return true;
    }

}

