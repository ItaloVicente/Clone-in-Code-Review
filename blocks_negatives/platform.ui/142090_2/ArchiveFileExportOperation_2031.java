            } catch (CoreException e) {
            }
            monitor.beginTask(DataTransferMessages.DataTransfer_exportingTitle, totalWork);
            if (resourcesToExport == null) {
                exportResource(resource);
            } else {
                exportSpecifiedResources();
            }

            try {
                exporter.finished();
            } catch (IOException e) {
                throw new InvocationTargetException(
                        e,
                        NLS.bind(DataTransferMessages.ZipExport_cannotClose, e.getMessage()));
            }
        } finally {
            monitor.done();
        }
    }

    /**
     *	Set this boolean indicating whether each exported resource's path should
     *	include containment hierarchies as dictated by its parents
     *
     *	@param value boolean
     */
    public void setCreateLeadupStructure(boolean value) {
        createLeadupStructure = value;
    }

    /**
     *	Set this boolean indicating whether exported resources should
     *	be compressed (as opposed to simply being stored)
     *
     *	@param value boolean
     */
    public void setUseCompression(boolean value) {
        useCompression = value;
    }

    /**
     * Set this boolean indicating whether the file should be output
     * in tar.gz format rather than .zip format.
     *
     * @param value boolean
     */
    public void setUseTarFormat(boolean value) {
    	useTarFormat = value;
    }
