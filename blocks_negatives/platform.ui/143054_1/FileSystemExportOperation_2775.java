            } catch (CoreException e) {
                errorTable.add(e.getStatus());
            }
            monitor.beginTask(DataTransferMessages.DataTransfer_exportingTitle, totalWork);
            if (resourcesToExport == null) {
                exportAllResources();
            } else {
                exportSpecifiedResources();
            }
        } finally {
            monitor.done();
        }
    }

    /**
     *	Set this boolean indicating whether a directory should be created for
     *	Folder resources that are explicitly passed for export
     *
     *	@param value boolean
     */
    public void setCreateContainerDirectories(boolean value) {
        createContainerDirectories = value;
    }

    /**
     *	Set this boolean indicating whether each exported resource's complete path should
     *	include containment hierarchies as dictated by its parents
     *
     *	@param value boolean
     */
    public void setCreateLeadupStructure(boolean value) {
        createLeadupStructure = value;
    }

    /**
     *	Set this boolean indicating whether exported resources should automatically
     *	overwrite existing files when a conflict occurs. If not
     *	query the user.
     *
     *	@param value boolean
     */
    public void setOverwriteFiles(boolean value) {
        if (value) {
