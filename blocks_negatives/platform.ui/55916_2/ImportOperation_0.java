                monitor.beginTask(DataTransferMessages.DataTransfer_importTask, 1000);
                ContainerGenerator generator = new ContainerGenerator(
                        destinationPath);
                monitor.worked(30);
                validateFiles(Arrays.asList(new Object[] { source }));
                monitor.worked(50);
                destinationContainer = generator
                        .generateContainer(new SubProgressMonitor(monitor, 50));
                importRecursivelyFrom(source, POLICY_DEFAULT);
                monitor.worked(90);
