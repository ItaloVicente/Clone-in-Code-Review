			String currentPath = tw.getPathString();
			if (result.containsConflicts() && !ignoreConflicts) {
				unmergedPaths.add(currentPath);
			}
			modifiedFiles.add(currentPath);
			addCheckoutMetadata(currentPath
