package org.eclipse.ui.internal.navigator.resources.actions;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.CopyProjectOperation;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;


    public static final String ID = PlatformUI.PLUGIN_ID + ".PasteAction";//$NON-NLS-1$

    private Shell shell;

    private Clipboard clipboard;

    public PasteAction(Shell shell, Clipboard clipboard) {
        super(WorkbenchNavigatorMessages.PasteAction_Past_); 
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(WorkbenchNavigatorMessages.PasteAction_Paste_selected_resource_s_); 
        setId(PasteAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "HelpId"); //$NON-NLS-1$
    }

    private IResource getTarget() {
        List<IResource> selectedResources = getSelectedResources();

        for (IResource resource : selectedResources) {
            if (resource instanceof IProject && !((IProject) resource).isOpen()) {
				return null;
			}
            if (resource.getType() == IResource.FILE) {
				resource = resource.getParent();
			}
            if (resource != null) {
				return resource;
			}
        }
        return null;
    }

    private boolean isLinked(IResource[] resources) {
        for (int i = 0; i < resources.length; i++) {
            if (resources[i].isLinked()) {
				return true;
			}
        }
        return false;
    }

    @Override
	public void run() {
        ResourceTransfer resTransfer = ResourceTransfer.getInstance();
        IResource[] resourceData = (IResource[]) clipboard
                .getContents(resTransfer);

        if (resourceData != null && resourceData.length > 0) {
            if (resourceData[0].getType() == IResource.PROJECT) {
                for (int i = 0; i < resourceData.length; i++) {
                    CopyProjectOperation operation = new CopyProjectOperation(
                            this.shell);
                    operation.copyProject((IProject) resourceData[i]);
                }
            } else {
                IContainer container = getContainer();

                CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(
                        this.shell);
                operation.copyResources(resourceData, container);
            }
            return;
        }

        FileTransfer fileTransfer = FileTransfer.getInstance();
        String[] fileData = (String[]) clipboard.getContents(fileTransfer);

        if (fileData != null) {
            IContainer container = getContainer();

            CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(
                    this.shell);
            operation.copyFiles(fileData, container);
        }
    }

    private IContainer getContainer() {
        List<IResource> selection = getSelectedResources();
        if (selection.get(0) instanceof IFile) {
			return ((IFile) selection.get(0)).getParent();
		}
        return (IContainer) selection.get(0);
    }

    @Override
	protected boolean updateSelection(IStructuredSelection selection) {
        if (!super.updateSelection(selection)) {
			return false;
		}

        final IResource[][] clipboardData = new IResource[1][];
        shell.getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
                ResourceTransfer resTransfer = ResourceTransfer.getInstance();
                clipboardData[0] = (IResource[]) clipboard
                        .getContents(resTransfer);
            }
        });
        IResource[] resourceData = clipboardData[0];
        boolean isProjectRes = resourceData != null && resourceData.length > 0
                && resourceData[0].getType() == IResource.PROJECT;

        if (isProjectRes) {
            for (int i = 0; i < resourceData.length; i++) {
                if (resourceData[i].getType() != IResource.PROJECT
                        || ((IProject) resourceData[i]).isOpen() == false) {
					return false;
				}
            }
            return true;
        }

        if (getSelectedNonResources().size() > 0) {
			return false;
		}

        IResource targetResource = getTarget();
        if (targetResource == null) {
			return false;
		}

        List<IResource> selectedResources = getSelectedResources();
        if (selectedResources.size() > 1) {
            for (IResource resource : selectedResources) {
                if (resource.getType() != IResource.FILE) {
					return false;
				}
                if (!targetResource.equals(resource.getParent())) {
					return false;
				}
            }
        }
        if (resourceData != null) {
            if (isLinked(resourceData)
                && targetResource.getType() != IResource.PROJECT
                && targetResource.getType() != IResource.FOLDER) {
				return false;
			}

            if (targetResource.getType() == IResource.FOLDER) {
                for (int i = 0; i < resourceData.length; i++) {
                    if (targetResource.equals(resourceData[i])) {
						return false;
					}
                }
            }
            return true;
        }
        TransferData[] transfers = clipboard.getAvailableTypes();
        FileTransfer fileTransfer = FileTransfer.getInstance();
        for (int i = 0; i < transfers.length; i++) {
            if (fileTransfer.isSupportedType(transfers[i])) {
				return true;
			}
        }
        return false;
    }
}

