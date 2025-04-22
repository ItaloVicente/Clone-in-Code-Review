			Path filePath = file.toPath();
			Path rootPath = getWizard().getImportJob().getRoot().toPath();
			if (filePath.startsWith(rootPath)) {
				if (rootPath.getParent() != null) {
					Path relative = rootPath.getParent().relativize(filePath);
					if (relative.getNameCount() > 0) {
						return relative.toString();
					}
