        IPath targetPath = targetResource.getLocation();
        if (targetPath != null
                && (targetPath.toFile().equals(new File(fileObjectPath)))) {
            errorTable.add(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
                    NLS.bind(DataTransferMessages.ImportOperation_targetSameAsSourceError, fileObjectPath), null));
            return;
        }

        InputStream contentStream = provider.getContents(fileObject);
        if (contentStream == null) {
            errorTable
                    .add(new Status(
                            IStatus.ERROR,
                            PlatformUI.PLUGIN_ID,
                            0,
                            NLS.bind(DataTransferMessages.ImportOperation_openStreamError, fileObjectPath),
                            null));
            return;
        }
