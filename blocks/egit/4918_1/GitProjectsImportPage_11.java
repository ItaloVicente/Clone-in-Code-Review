				private ProjectFolder createDeepFolder(File file, File root) {
					File parent = file.getParentFile();
					LinkedList<File> subfolders = new LinkedList<File>();
					while(!root.equals(parent)) {
						subfolders.add(parent);
						parent = parent.getParentFile();
					}

					subfolders.removeFirst();

					ProjectFolder currentFolder = rootFolder;
					while(!subfolders.isEmpty()) {
						File newChild = subfolders.removeLast();
						if(!currentFolder.hasFolder(newChild.getName())) {
							ProjectFolder newFolder = new ProjectFolder(newChild.getName());
							currentFolder.addProjectFolder(newFolder);
							currentFolder = newFolder;
						} else {
							currentFolder = currentFolder.getFolder(newChild.getName());
						}
					}

					return currentFolder;
				}

