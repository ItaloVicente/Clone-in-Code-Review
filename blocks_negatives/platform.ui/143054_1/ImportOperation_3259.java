        IContainer containerResource;
        try {
            containerResource = getDestinationContainerFor(fileObject);
        } catch (CoreException e) {
            IStatus coreStatus = e.getStatus();
            String newMessage = NLS.bind(DataTransferMessages.ImportOperation_coreImportError, fileObject, coreStatus.getMessage());
            IStatus status = new Status(coreStatus.getSeverity(), coreStatus
                    .getPlugin(), coreStatus.getCode(), newMessage, null);
            errorTable.add(status);
            return;
        }

        String fileObjectPath = provider.getFullPath(fileObject);
