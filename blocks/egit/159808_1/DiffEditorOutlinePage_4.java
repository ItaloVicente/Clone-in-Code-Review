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
