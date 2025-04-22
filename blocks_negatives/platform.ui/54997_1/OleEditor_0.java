    /**
     * The resource listener updates the receiver when
     * a change has occurred.
     */
    private IResourceChangeListener resourceListener = new IResourceChangeListener() {

        /*
         * @see IResourceChangeListener#resourceChanged(IResourceChangeEvent)
         */
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

        }

    };

    private OleFrame clientFrame;

    private OleClientSite clientSite;

    private File source;

    private IFile resource;

    private Image oleTitleImage;

    boolean sourceDeleted = false;

    boolean sourceChanged = false;

    /**
     * Keep track of whether we have an active client so we do not
     * deactivate multiple times
     */
    private boolean clientActive = false;

    /**
     * Keep track of whether we have activated OLE or not as some applications
     * will only allow single activations.
     */
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

    private void activateClient(IWorkbenchPart part) {
        if (part == this) {
            oleActivate();
            this.clientActive = true;
        }
    }

    /**
     * createPartControl method comment.
     */
    public void createPartControl(Composite parent) {

        clientFrame = new OleFrame(parent, SWT.CLIP_CHILDREN);
        clientFrame.setBackground(JFaceColors.getBannerBackground(clientFrame
                .getDisplay()));

        initializeWorkbenchMenus();

        createClientSite();
        updateDirtyFlag();
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

    private void deactivateClient(IWorkbenchPart part) {
        if (part == this && clientActive) {
            this.clientActive = false;
            this.oleActivated = false;
        }
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

    /**
     *	Print this object's contents
     */
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

    /**
     *	Save the viewer's contents to the source file system file
     */
    public void doSave(final IProgressMonitor monitor) {
        if (clientSite == null)
            return;
        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {

            /*
             *  (non-Javadoc)
             * @see java.lang.Runnable#run()
             */
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

    /* (non-Javadoc)
     * Initializes the editor when created from scratch.
     *
     * This method is called soon after part construction and marks
     * the start of the extension lifecycle.  At the end of the
     * extension lifecycle <code>shutdown</code> will be invoked
     * to terminate the lifecycle.
     *
     * @param container an interface for communication with the part container
     * @param input The initial input element for the editor.  In most cases
     *    it is an <code>IFile</code> but other types are acceptable.
     * @see IWorkbenchPart#shutdown
     */
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

    /**
     * Validates the given input
     *
     * @param input the editor input to validate
     * @throws PartInitException if the editor input is not OK
     */
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
        Vector containerItems = new Vector();

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

    /*
     *  (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#isDirty()
     */
    public boolean isDirty() {
        /*Return only if we have a clientSite which is dirty
         as this can be asked before anything is opened*/
        return clientSite != null && clientSite.isDirty();
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
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

    /*
     *  (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
    }

    /**
     * Make ole active so that the controls are rendered.
     */
    private void oleActivate() {
        if (clientSite == null || clientFrame == null
                || clientFrame.isDisposed())
            return;

        if (!oleActivated) {
            clientSite.doVerb(OLE.OLEIVERB_SHOW);
            oleActivated = true;
            String progId = clientSite.getProgramID();
                handleWord();
            }
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#setInputWithNotify(org.eclipse.ui.IEditorInput)
     */
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

    /*
     * See IEditorPart.isSaveOnCloseNeeded()
     */
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
