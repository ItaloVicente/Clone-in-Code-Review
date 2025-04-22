					selectedProjects = new ProjectRecord[0];
					Collection<File> files = new ArrayList<File>();
					monitor.worked(10);
					if (directory.isDirectory()) {

						if (!collectProjectFilesFromDirectory(files, directory,
								null, monitor)) {
							return;
						}
						Iterator<File> filesIterator = files.iterator();
						selectedProjects = new ProjectRecord[files.size()];
						int index = 0;
						monitor.worked(50);
						monitor
								.subTask(UIText.WizardProjectsImportPage_ProcessingMessage);
						while (filesIterator.hasNext()) {
							File file = filesIterator.next();
							selectedProjects[index] = new ProjectRecord(file);
							index++;
