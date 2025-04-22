        setErrorMessage(DataTransferMessages.FileImport_invalidSource);
        return false;
    }

    /**
     *	Execute the passed import operation.  Answer a boolean indicating success.
     */
    protected boolean executeImportOperation(ImportOperation op) {
        initializeOperation(op);

        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            displayErrorDialog(e.getTargetException());
            return false;
        }

        IStatus status = op.getStatus();
        if (!status.isOK()) {
            ErrorDialog
                    .openError(getContainer().getShell(), DataTransferMessages.FileImport_importProblems,
                            null, // no special message
                            status);
            return false;
        }

        return true;
    }

    /**
     *	The Finish button was pressed.  Try to do the required work now and answer
     *	a boolean indicating success.  If false is returned then the wizard will
     *	not close.
     *
     * @return boolean
     */
    public boolean finish() {
        if (!ensureSourceIsValid()) {
