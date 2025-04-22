						Iterator<File> filesIterator = files.iterator();
						monitor.worked(50);
						monitor
								.subTask(UIText.WizardProjectsImportPage_ProcessingMessage);
						while (filesIterator.hasNext()) {
							File file = filesIterator.next();
