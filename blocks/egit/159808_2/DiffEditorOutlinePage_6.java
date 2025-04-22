
		private void compactify(Folder folder) {
			if (folder.files.isEmpty() && folder.folders.size() == 1) {
				Folder parent = parents.get(folder);
				Folder child = folder.folders.get(0);
				if (parent != null) {
					child.name = folder.name + SLASH + child.name;
					parent.folders.remove(folder);
					parent.folders.add(child);
					parents.remove(folder);
					parents.put(child, parent);
				}
			}
			new ArrayList<>(folder.folders).forEach(f -> compactify(f));
		}

		private Folder computeRootFolder(String path) {
			return rootFolders.computeIfAbsent(path, key -> {
				Folder newFolder = new Folder(key);
				return newFolder;
			});
		}
