			if (file != null)
				removeEmptyParents(file);

			for (String path : updated.keySet()) {
				file = new File(repo.getWorkTree()
				if (!file.getParentFile().mkdirs()) {
				}
