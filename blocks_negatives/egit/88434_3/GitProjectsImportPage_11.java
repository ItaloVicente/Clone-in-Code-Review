						monitor.worked(50);
						monitor
								.subTask(UIText.WizardProjectsImportPage_ProcessingMessage);
						while (filesIterator.hasNext()) {
							File file = filesIterator.next();
							selectedProjects[index] = new ProjectRecord(file);
							index++;
						}

						if (files.isEmpty())
