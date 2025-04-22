					if (!collectProjectFilesFromProvider(files, child2, 0,
							monitor)) {
						return;
					}
					Iterator filesIterator2 = files.iterator();
					selectedProjects = new ProjectRecord[files.size()];
					int index2 = 0;
					monitor.worked(50);
					monitor
							.subTask(DataTransferMessages.WizardProjectsImportPage_ProcessingMessage);
					while (filesIterator2.hasNext()) {
						selectedProjects[index2++] = (ProjectRecord) filesIterator2
								.next();
