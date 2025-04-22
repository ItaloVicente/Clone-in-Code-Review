					.anyMatch(full -> full.isPrefixOf(filePath))) {
				progress.worked(1);
				return;
			}
			if (!roots.keySet().stream()
					.anyMatch(root -> root.isPrefixOf(filePath))) {
				needRefresh.add(path);
