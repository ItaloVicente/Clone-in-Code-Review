                }
            }
        else if (FileTransfer.getInstance().isSupportedType(transferType)) {
            String[] sourceNames = (String[]) FileTransfer.getInstance()
                    .nativeToJava(transferType);
            if (sourceNames == null) {
                sourceNames = new String[0];
            }
            CopyFilesAndFoldersOperation copyOperation = new CopyFilesAndFoldersOperation(
                    getShell());
            message = copyOperation.validateImportDestination(destination,
                    sourceNames);
        }
        if (message != null) {
            return error(message);
        }
        return ok();
    }
