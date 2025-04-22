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
import org.eclipse.ui.IPartListener;
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

public class OleEditor extends EditorPart {

    private IResourceChangeListener resourceListener = new IResourceChangeListener() {

        public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta mainDelta = event.getDelta();
            if (mainDelta == null)
                return;
            IResourceDelta affectedElement = mainDelta.findMember(resource
                    .getFullPath());
            if (affectedElement != null)
                processDelta(affectedElement);
        }

        private boolean processDelta(final IResourceDelta delta) {

            Runnable changeRunnable = null;

            switch (delta.getKind()) {
            case IResourceDelta.REMOVED:
                if ((IResourceDelta.MOVED_TO & delta.getFlags()) != 0) {
                    changeRunnable = new Runnable() {
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

            return true; // because we are sitting on files anyway
        }

    };

    private OleFrame clientFrame;

    private OleClientSite clientSite;

    private File source;

    private IFile resource;

    private Image oleTitleImage;

    boolean sourceDeleted = false;

    boolean sourceChanged = false;

    private boolean clientActive = false;

    private boolean oleActivated = false;

    private IPartListener partListener = new IPartListener() {
        public void partActivated(IWorkbenchPart part) {
            activateClient(part);
        }

        public void partBroughtToTop(IWorkbenchPart part) {
        }

        public void partClosed(IWorkbenchPart part) {
        }

        public void partOpened(IWorkbenchPart part) {
        }

        public void partDeactivated(IWorkbenchPart part) {
            deactivateClient(part);
        }
    };

    private static final String RENAME_ERROR_TITLE = OleMessages
            .getString("OleEditor.errorSaving"); //$NON-NLS-1$

    private static final String OLE_EXCEPTION_TITLE = OleMessages
            .getString("OleEditor.oleExceptionTitle"); //$NON-NLS-1$

    private static final String OLE_EXCEPTION_MESSAGE = OleMessages
            .getString("OleEditor.oleExceptionMessage"); //$NON-NLS-1$

    private static final String OLE_CREATE_EXCEPTION_MESSAGE = OleMessages
            .getString("OleEditor.oleCreationExceptionMessage"); //$NON-NLS-1$

    private static final String OLE_CREATE_EXCEPTION_REASON = OleMessages
            .getString("OleEditor.oleCreationExceptionReason"); //$NON-NLS-1$

    private static final String SAVE_ERROR_TITLE = OleMessages
            .getString("OleEditor.savingTitle"); //$NON-NLS-1$

    private static final String SAVE_ERROR_MESSAGE = OleMessages
            .getString("OleEditor.savingMessage"); //$NON-NLS-1$

    public OleEditor() {
    }

    private void activateClient(IWorkbenchPart part) {
        if (part == this) {
            oleActivate();
            this.clientActive = true;
        }
    }

    public void createPartControl(Composite parent) {

        clientFrame = new OleFrame(parent, SWT.CLIP_CHILDREN);
        clientFrame.setBackground(JFaceColors.getBannerBackground(clientFrame
                .getDisplay()));

        initializeWorkbenchMenus();

        createClientSite();
        updateDirtyFlag();
    }


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

    private void deactivateClient(IWorkbenchPart part) {
        if (part == this && clientActive) {
            this.clientActive = false;
            this.oleActivated = false;
        }
    }

    private void displayErrorDialog(String title, String message) {
        Shell parent = null;
        if (getClientSite() != null)
            parent = getClientSite().getShell();
        MessageDialog.openError(parent, title, message);
    }

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

        if (getSite() != null && getSite().getPage() != null)
            getSite().getPage().removePartListener(partListener);

    }

    public void doPrint() {
        if (clientSite == null)
            return;
        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {
            public void run() {
                clientSite.exec(OLE.OLECMDID_PRINT,
                        OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
            }
        });
    }

    public void doSave(final IProgressMonitor monitor) {
        if (clientSite == null)
            return;
        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {

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

    public OleClientSite getClientSite() {
        return clientSite;
    }

    public File getSourceFile() {
        return source;
    }

    private void handleWord() {
        OleAutomation dispInterface = new OleAutomation(clientSite);
        int[] appId = dispInterface
                .getIDsOfNames(new String[] { "Application" }); //$NON-NLS-1$
        if (appId != null) {
            Variant pVarResult = dispInterface.getProperty(appId[0]);
            if (pVarResult != null) {
                OleAutomation application = pVarResult.getAutomation();
                int[] dispid = application
                        .getIDsOfNames(new String[] { "DisplayScrollBars" }); //$NON-NLS-1$
                if (dispid != null) {
                    Variant rgvarg = new Variant(true);
                    application.setProperty(dispid[0], rgvarg);
                }
                application.dispose();
            }
        }
        dispInterface.dispose();
    }

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

        site.getPage().addPartListener(partListener);

    }
    
    private boolean validatePathEditorInput(IEditorInput input) throws PartInitException {
    	IPathEditorInput pathEditorInput = (IPathEditorInput)input.getAdapter(IPathEditorInput.class);
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
        Vector containerItems = new Vector();

        IWorkbenchWindow window = getSite().getWorkbenchWindow();

        for (int i = 0; i < menuBar.getItemCount(); i++) {
            MenuItem item = menuBar.getItem(i);
            String id = ""; //$NON-NLS-1$
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

    public boolean isDirty() {
         as this can be asked before anything is opened*/
        return clientSite != null && clientSite.isDirty();
    }

    public boolean isSaveAsAllowed() {
        return true;
    }

    public boolean isSaveNeeded() {
        return isDirty();
    }

    private boolean saveFile(File file) {

        File tempFile = new File(file.getAbsolutePath() + ".tmp"); //$NON-NLS-1$
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

    private WorkspaceModifyOperation saveNewFileOperation() {

        return new WorkspaceModifyOperation() {
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

    public void setFocus() {
    }

    private void oleActivate() {
        if (clientSite == null || clientFrame == null
                || clientFrame.isDisposed())
            return;

        if (!oleActivated) {
            clientSite.doVerb(OLE.OLEIVERB_SHOW);
            oleActivated = true;
            String progId = clientSite.getProgramID();
            if (progId != null && progId.startsWith("Word.Document")) { //$NON-NLS-1$
                handleWord();
            }
        }
    }

    protected void setInputWithNotify(IEditorInput input) {
    	IPathEditorInput pathEditorInput = (IPathEditorInput)input.getAdapter(IPathEditorInput.class);
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

    private static boolean usesStorageFiles(String progID) {
        return (progID != null && (progID.startsWith("Word.", 0) //$NON-NLS-1$
                || progID.startsWith("MSGraph", 0) //$NON-NLS-1$
                || progID.startsWith("PowerPoint", 0) //$NON-NLS-1$
        || progID.startsWith("Excel", 0))); //$NON-NLS-1$
    }

    private void sourceChanged(IFile newFile) {

        FileEditorInput newInput = new FileEditorInput(newFile);
        setInputWithNotify(newInput);
        sourceChanged = true;
        setPartName(newInput.getName());

    }

    public boolean isSaveOnCloseNeeded() {
        return !sourceDeleted && super.isSaveOnCloseNeeded();
    }

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
