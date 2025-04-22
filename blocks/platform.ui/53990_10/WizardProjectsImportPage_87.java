					Iterator filesIterator3 = files.iterator();
					selectedProjects = new ProjectRecord[files.size()];
					int index3 = 0;
					monitor.worked(50);
					monitor
							.subTask(DataTransferMessages.WizardProjectsImportPage_ProcessingMessage);
					while (filesIterator3.hasNext()) {
						File file = (File) filesIterator3.next();
						selectedProjects[index3] = new ProjectRecord(file);
						index3++;
					}
				} else {
					monitor.worked(60);
