            Object fileSystemObject = filesEnum.next();
            if (source == null) {
                IPath sourcePath = new Path(provider
                        .getFullPath(fileSystemObject)).removeLastSegments(1);
                if (provider.isFolder(fileSystemObject) && sourcePath.isEmpty()) {
                    errorTable.add(new Status(IStatus.INFO,
                            PlatformUI.PLUGIN_ID, 0, DataTransferMessages.ImportOperation_cannotCopy,
                            null));
                    continue;
                }
                source = sourcePath.toFile();
            }
