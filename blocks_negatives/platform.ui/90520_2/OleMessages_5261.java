/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.editorsupport.win32;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 */
public class OleEditor extends EditorPart {

    /**
     * The resource listener updates the receiver when
     * a change has occurred.
     */
    private IResourceChangeListener resourceListener = new IResourceChangeListener() {

        @Override
		public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta mainDelta = event.getDelta();
            if (mainDelta == null)
                return;
            IResourceDelta affectedElement = mainDelta.findMember(resource
                    .getFullPath());
            if (affectedElement != null)
                processDelta(affectedElement);
        }

        /*
         * Process the delta for the receiver
         */
        private boolean processDelta(final IResourceDelta delta) {

            Runnable changeRunnable = null;

            switch (delta.getKind()) {
            case IResourceDelta.REMOVED:
                if ((IResourceDelta.MOVED_TO & delta.getFlags()) != 0) {
                    changeRunnable = new Runnable() {
                        @Override
						public void run() {
                            IPath path = delta.getMovedToPath();
                            IFile newFile = delta.getResource().getWorkspace()
                                    .getRoot().getFile(path);
                            if (newFile != null) {
                                sourceChanged(newFile);
                            }
                        }
                    };
                } else {
                    changeRunnable = new Runnable() {
                        @Override
						public void run() {
                            sourceDeleted = true;
                            getSite().getPage().closeEditor(OleEditor.this,
                                    true);
                        }
                    };

                }

                break;
            }

            if (changeRunnable != null)
                update(changeRunnable);

        }

    };

    private OleFrame clientFrame;

    private OleClientSite clientSite;

    private File source;

    private IFile resource;

    private Image oleTitleImage;

    boolean sourceDeleted = false;

    boolean sourceChanged = false;

    private static final String RENAME_ERROR_TITLE = OleMessages

    private static final String OLE_EXCEPTION_TITLE = OleMessages

    private static final String OLE_EXCEPTION_MESSAGE = OleMessages

    private static final String OLE_CREATE_EXCEPTION_MESSAGE = OleMessages

    private static final String OLE_CREATE_EXCEPTION_REASON = OleMessages

    private static final String SAVE_ERROR_TITLE = OleMessages

    private static final String SAVE_ERROR_MESSAGE = OleMessages

    /**
     * Return a new ole editor.
     */
    public OleEditor() {
    }

    @Override
	public void createPartControl(Composite parent) {

        clientFrame = new OleFrame(parent, SWT.CLIP_CHILDREN);
        clientFrame.setBackground(JFaceColors.getBannerBackground(clientFrame
                .getDisplay()));

        initializeWorkbenchMenus();

        createClientSite();
        updateDirtyFlag();

		oleActivate();
    }

    /**
     * Create the client site for the receiver
     */

    private void createClientSite() {
        if (clientFrame == null || clientFrame.isDisposed())
            return;
        try {
            clientSite = new OleClientSite(clientFrame, SWT.NONE, source);
        } catch (SWTException exception) {

            IStatus errorStatus = new Status(IStatus.ERROR,
                    WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR,
                    OLE_CREATE_EXCEPTION_REASON, exception);
            ErrorDialog.openError(null, OLE_EXCEPTION_TITLE, OLE_CREATE_EXCEPTION_MESSAGE, errorStatus);
            return;
        }
        clientSite.setBackground(JFaceColors.getBannerBackground(clientFrame
                .getDisplay()));

    }

    /**
     * Display an error dialog with the supplied title and message.
     * @param title
     * @param message
     */
    private void displayErrorDialog(String title, String message) {
        Shell parent = null;
        if (getClientSite() != null)
            parent = getClientSite().getShell();
        MessageDialog.openError(parent, title, message);
    }

    /**
     * @see IWorkbenchPart#dispose
     */
    @Override
	public void dispose() {
        if (resource != null) {
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(
                    resourceListener);
            resource = null;
        }

        if (oleTitleImage != null) {
            oleTitleImage.dispose();
            oleTitleImage = null;
        }
    }

    /**
     *	Print this object's contents
     */
    public void doPrint() {
        if (clientSite == null)
            return;
        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {
            @Override
			public void run() {
                clientSite.exec(OLE.OLECMDID_PRINT,
                        OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
            }
        });
    }

    /**
     *	Save the viewer's contents to the source file system file
     */
    @Override
	public void doSave(final IProgressMonitor monitor) {
        if (clientSite == null)
            return;
        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {

            @Override
			public void run() {

                if (!sourceChanged) {
                    int result = clientSite.queryStatus(OLE.OLECMDID_SAVE);
                    if ((result & OLE.OLECMDF_ENABLED) != 0) {
                        result = clientSite.exec(OLE.OLECMDID_SAVE,
                                OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
                        if (result == OLE.S_OK) {
                            try {
                                resource.refreshLocal(IResource.DEPTH_ZERO,
                                        monitor);
                            } catch (CoreException ex) {
                            }
                            return;
                        }
                        displayErrorDialog(OLE_EXCEPTION_TITLE,
                                OLE_EXCEPTION_MESSAGE + String.valueOf(result));
                        return;
                    }
                }
                if (saveFile(source)) {
                    try {
                    	if (resource != null)
                    		resource.refreshLocal(IResource.DEPTH_ZERO, monitor);
                    } catch (CoreException ex) {
                    }
                } else
                    displayErrorDialog(SAVE_ERROR_TITLE, SAVE_ERROR_MESSAGE
                            + source.getName());
            }
        });
    }

    /**
     *	Save the viewer's contents into the provided resource.
     */
    @Override
	public void doSaveAs() {
        if (clientSite == null)
            return;
        WorkspaceModifyOperation op = saveNewFileOperation();
        Shell shell = clientSite.getShell();
        try {
            new ProgressMonitorDialog(shell).run(false, true, op);
        } catch (InterruptedException interrupt) {
        } catch (InvocationTargetException invocationException) {
            MessageDialog.openError(shell, RENAME_ERROR_TITLE,
                    invocationException.getTargetException().getMessage());
        }

    }

    /**
     *	Answer self's client site
     *
     *	@return org.eclipse.swt.ole.win32.OleClientSite
     */
    public OleClientSite getClientSite() {
        return clientSite;
    }

    /**
     *	Answer the file system representation of self's input element
     *
     *	@return java.io.File
     */
    public File getSourceFile() {
        return source;
    }

    private void handleWord() {
        OleAutomation dispInterface = new OleAutomation(clientSite);
        int[] appId = dispInterface
        if (appId != null) {
            Variant pVarResult = dispInterface.getProperty(appId[0]);
            if (pVarResult != null) {
                OleAutomation application = pVarResult.getAutomation();
                int[] dispid = application
                if (dispid != null) {
                    Variant rgvarg = new Variant(true);
                    application.setProperty(dispid[0], rgvarg);
                }
                application.dispose();
            }
        }
        dispInterface.dispose();
    }

    @Override
	public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {

    	validatePathEditorInput(input);

        setSite(site);
        setInputWithNotify(input);

        setPartName(input.getName());
        setTitleToolTip(input.getToolTipText());
        ImageDescriptor desc = input.getImageDescriptor();
        if (desc != null) {
            oleTitleImage = desc.createImage();
            setTitleImage(oleTitleImage);
        }
    }

    /**
     * Validates the given input
     *
     * @param input the editor input to validate
     * @throws PartInitException if the editor input is not OK
     */
    private boolean validatePathEditorInput(IEditorInput input) throws PartInitException {
		IPathEditorInput pathEditorInput = Adapters.adapt(input, IPathEditorInput.class);
        if (pathEditorInput == null)
            throw new PartInitException(OleMessages.format(
                    "OleEditor.invalidInput", new Object[] { input })); //$NON-NLS-1$

        IPath path = pathEditorInput.getPath();

        if (!(new File(path.toOSString()).exists()))
            throw new PartInitException(
                    OleMessages
                            .format(
                                    "OleEditor.noFileInput", new Object[] { path.toOSString() })); //$NON-NLS-1$
        return true;
    }

    /**
     *	Initialize the workbench menus for proper merging
     */
    protected void initializeWorkbenchMenus() {
        if (clientFrame == null || clientFrame.isDisposed())
            return;
        Shell shell = clientFrame.getShell();
        Menu menuBar = shell.getMenuBar();
        if (menuBar == null) {
            menuBar = new Menu(shell, SWT.BAR);
            shell.setMenuBar(menuBar);
        }

        MenuItem[] windowMenu = new MenuItem[1];
        MenuItem[] fileMenu = new MenuItem[1];
		Vector<MenuItem> containerItems = new Vector<MenuItem>();

        IWorkbenchWindow window = getSite().getWorkbenchWindow();

        for (int i = 0; i < menuBar.getItemCount(); i++) {
            MenuItem item = menuBar.getItem(i);
            if (item.getData() instanceof IMenuManager)
                id = ((IMenuManager) item.getData()).getId();
            if (id.equals(IWorkbenchActionConstants.M_FILE))
                fileMenu[0] = item;
            else if (id.equals(IWorkbenchActionConstants.M_WINDOW))
                windowMenu[0] = item;
            else {
                if (window.isApplicationMenu(id)) {
                    containerItems.addElement(item);
                }
            }
        }
        MenuItem[] containerMenu = new MenuItem[containerItems.size()];
        containerItems.copyInto(containerMenu);
        clientFrame.setFileMenus(fileMenu);
        clientFrame.setContainerMenus(containerMenu);
        clientFrame.setWindowMenus(windowMenu);
    }

    @Override
	public boolean isDirty() {
        /*Return only if we have a clientSite which is dirty
         as this can be asked before anything is opened*/
        return clientSite != null && clientSite.isDirty();
    }

    @Override
	public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     *Since we don't know when a change has been made, always answer true
     * @return <code>false</code> if it was not opened and <code>true</code>
     * only if it is dirty
     */
    public boolean isSaveNeeded() {
        return isDirty();
    }

    /**
     * Save the supplied file using the SWT API.
     * @param file java.io.File
     * @return <code>true</code> if the save was successful
     */
    private boolean saveFile(File file) {

        file.renameTo(tempFile);
        boolean saved = false;
        if (OLE.isOleFile(file) || usesStorageFiles(clientSite.getProgramID())) {
            saved = clientSite.save(file, true);
        } else {
            saved = clientSite.save(file, false);
        }

        if (saved) {
            tempFile.delete();
            return true;
        }
        tempFile.renameTo(file);
        return false;
    }

    /**
     * Save the new File using the client site.
     * @return WorkspaceModifyOperation
     */
    private WorkspaceModifyOperation saveNewFileOperation() {

        return new WorkspaceModifyOperation() {
            @Override
			public void execute(final IProgressMonitor monitor)
                    throws CoreException {
                SaveAsDialog dialog = new SaveAsDialog(clientFrame.getShell());
                IFile sFile = ResourceUtil.getFile(getEditorInput());
                if (sFile != null) {
                    dialog.setOriginalFile(sFile);
                    dialog.open();

                    IPath newPath = dialog.getResult();
                    if (newPath == null)
                        return;

                    if (dialog.getReturnCode() == Window.OK) {
                        String projectName = newPath.segment(0);
                        newPath = newPath.removeFirstSegments(1);
                        IProject project = resource.getWorkspace().getRoot()
                                .getProject(projectName);
                        newPath = project.getLocation().append(newPath);
                        File newFile = newPath.toFile();
                        if (saveFile(newFile)) {
                            IFile newResource = resource.getWorkspace().getRoot()
                                    .getFileForLocation(newPath);
                            if (newResource != null) {
                                sourceChanged(newResource);
                                newResource.refreshLocal(IResource.DEPTH_ZERO,
                                        monitor);
                            }
                        } else {
                            displayErrorDialog(SAVE_ERROR_TITLE, SAVE_ERROR_MESSAGE
                                    + newFile.getName());
                            return;
                        }
                    }
                }
            }
        };

    }

    @Override
	public void setFocus() {
		if (clientSite != null)
			clientSite.setFocus();
    }

    /**
     * Make ole active so that the controls are rendered.
     */
    private void oleActivate() {
        if (clientSite == null || clientFrame == null
                || clientFrame.isDisposed())
            return;

		clientSite.doVerb(OLE.OLEIVERB_SHOW);
		String progId = clientSite.getProgramID();
			handleWord();
		}
    }

    @Override
	protected void setInputWithNotify(IEditorInput input) {
		IPathEditorInput pathEditorInput = Adapters.adapt(input, IPathEditorInput.class);
    	if (pathEditorInput != null)
    		source = new File(pathEditorInput.getPath().toOSString());

        if (input instanceof IFileEditorInput) {
        	if (resource == null)
        		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
        	resource = ((IFileEditorInput)input).getFile();
        } else if (resource != null) {
        	ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
        	resource = null;
        }

        super.setInputWithNotify(input);
    }

    /**
     * See if it is one of the known types that use OLE Storage.
     * @param progID the type to test
     * @return <code>true</code> if it is one of the known types
     */
    private static boolean usesStorageFiles(String progID) {
        return (progID != null && (progID.startsWith("Word.", 0) //$NON-NLS-1$
                || progID.startsWith("MSGraph", 0) //$NON-NLS-1$
                || progID.startsWith("PowerPoint", 0) //$NON-NLS-1$
        || progID.startsWith("Excel", 0))); //$NON-NLS-1$
    }

    /**
     * The source has changed to the newFile. Update
     * editors and set any required flags
     * @param newFile The file to get the new contents from.
     */
    private void sourceChanged(IFile newFile) {

        FileEditorInput newInput = new FileEditorInput(newFile);
        setInputWithNotify(newInput);
        sourceChanged = true;
        setPartName(newInput.getName());

    }

    @Override
	public boolean isSaveOnCloseNeeded() {
        return !sourceDeleted && super.isSaveOnCloseNeeded();
    }

    /**
     * Posts the update code "behind" the running operation.
     *
     * @param runnable the update code
     */
    private void update(Runnable runnable) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
        if (windows != null && windows.length > 0) {
            Display display = windows[0].getShell().getDisplay();
            display.asyncExec(runnable);
        } else
            runnable.run();
    }

    private boolean isDirty = false;
    private void updateDirtyFlag() {
    	final Runnable dirtyFlagUpdater = new Runnable() {
			@Override
			public void run() {
				if (clientSite == null || resource == null) return;
				boolean dirty = isDirty();
				if (isDirty != dirty) {
					isDirty = dirty;
					firePropertyChange(PROP_DIRTY);
				}
				clientSite.getDisplay().timerExec(1000, this);
			}
    	};
    	dirtyFlagUpdater.run();
    }
}
