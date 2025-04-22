			final int nonZeroMode = modeBase != 0 ? modeBase
					: modeOurs != 0 ? modeOurs : modeTheirs;
			final IResource resource = getResourceHandleForLocation(repository,
					treeWalk.getPathString(),
					FileMode.fromBits(nonZeroMode) == FileMode.TREE);

			if (resource != null) {
				IPath workspacePath = resource.getFullPath();
