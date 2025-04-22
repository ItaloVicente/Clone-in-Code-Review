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
