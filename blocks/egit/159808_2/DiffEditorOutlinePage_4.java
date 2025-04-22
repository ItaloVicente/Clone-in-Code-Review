			if (compactTree) {
				for (FileDiffRegion range : ranges) {
					String path = range.getDiff().getPath();
					Folder folder = computeRootFolder(SLASH);
					String[] segments = path.split(SLASH);
					for (int i = 0; i < segments.length - 1; i++) {
						String segment = segments[i];
						Folder newFolder = null;
						for (Folder childFolder : folder.folders) {
							if (childFolder.name.equals(segment)) {
								newFolder = childFolder;
								break;
							}
						}
						if (newFolder == null) {
							newFolder = new Folder(segment);
							folder.folders.add(newFolder);
						}
						parents.put(newFolder, folder);
						folder = newFolder;
					}
					folder.files.add(range);
					parents.put(range, folder);
