        return true;
    }

    /**
     *  Export the passed resource and recursively export all of its child resources
     *  (iff it's a container).  Answer a boolean indicating success.
     */
    protected boolean executeExportOperation(ArchiveFileExportOperation op) {
        op.setCreateLeadupStructure(createDirectoryStructureButton
                .getSelection());
        op.setUseCompression(compressContentsCheckbox.getSelection());
        op.setIncludeLinkedResources(resolveLinkedResourcesCheckbox.getSelection());
        op.setUseTarFormat(targzFormatButton.getSelection());

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
            ErrorDialog.openError(getContainer().getShell(),
                    DataTransferMessages.DataTransfer_exportProblems,
                    null, // no special message
                    status);
            return false;
        }

        return true;
    }

    /**
     * The Finish button was pressed.  Try to do the required work now and answer
     * a boolean indicating success.  If false is returned then the wizard will
     * not close.
     * @returns boolean
     */
    @Override
