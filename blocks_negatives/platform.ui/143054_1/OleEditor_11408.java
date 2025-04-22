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
