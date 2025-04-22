						if (!collectProjectFilesFromProvider(files, child, 0,
								monitor)) {
							return;
						}
						Iterator filesIterator = files.iterator();
						selectedProjects = new ProjectRecord[files.size()];
						int index = 0;
						monitor.worked(50);
						monitor
								.subTask(DataTransferMessages.WizardProjectsImportPage_ProcessingMessage);
						while (filesIterator.hasNext()) {
							selectedProjects[index++] = (ProjectRecord) filesIterator
									.next();
						}
