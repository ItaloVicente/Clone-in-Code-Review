				compactify();
			} else {
				for (FileDiffRegion range : ranges) {
					String path = range.getDiff().getPath();
					int i = path.lastIndexOf('/');
					if (i > 0) {
						path = path.substring(0, i);
					} else {
						path = SLASH;
					}
					Folder folder = computeRootFolder(path);
					folder.files.add(range);
					parents.put(range, folder);
				}
			}
		}

		private void compactify() {
			Folder root = rootFolders.get(SLASH);
			compactify(root);
			if (root.files.isEmpty()) {
				rootFolders.clear();
				root.folders.forEach(f -> {
					parents.remove(f);
					rootFolders.put(f.name, f);
