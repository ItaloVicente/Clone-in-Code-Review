							ProjectRecord projectRecord = new ProjectRecord(file);

							ProjectFolder folderForProject = rootFolder;
							if(!file.getParentFile().equals(directory)) {
								folderForProject = createDeepFolder(file, directory);
							}

							folderForProject.addProject(projectRecord);
							availableProjects.add(projectRecord);
