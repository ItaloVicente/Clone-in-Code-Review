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
