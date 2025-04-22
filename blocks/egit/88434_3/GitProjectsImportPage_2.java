						} else {
							progress.subTask(
									UIText.WizardProjectsImportPage_ProcessingMessage);
							while (filesIterator.hasNext()) {
								File file = filesIterator.next();
								selectedProjects[index] = new ProjectRecord(
										file);
								index++;
								progress.worked(1);
							}
						}
