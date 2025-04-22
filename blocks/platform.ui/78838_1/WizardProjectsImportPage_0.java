			String path;
			if (projectSystemFile == null) {
				path = structureProvider.getFullPath(parent);
			} else {
				path = projectSystemFile.getParent();
			}
