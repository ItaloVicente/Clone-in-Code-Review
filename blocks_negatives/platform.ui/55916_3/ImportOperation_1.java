                int creationCount = selectedFiles.size();
                monitor.beginTask(DataTransferMessages.DataTransfer_importTask, creationCount + 100);
                ContainerGenerator generator = new ContainerGenerator(
                        destinationPath);
                monitor.worked(30);
                validateFiles(selectedFiles);
                monitor.worked(50);
                destinationContainer = generator
                        .generateContainer(new SubProgressMonitor(monitor, 50));
                importFileSystemObjects(selectedFiles);
                monitor.done();
